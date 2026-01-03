/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public abstract class FileUtils{

	//recursively build a list of Files in a directory based on a FileFilter, sort the resulting list
	public final static ArrayList<File> getAllFilesRecursive(File path,FileFilter filter){
		ArrayList<File> files=new ArrayList<File>();
		File[] filelist=path.listFiles(filter);
		int l=filelist.length;
		for(int i=0;i<l;i++){
			if(filelist[i].isDirectory()){
				files.addAll(getAllFilesRecursive(filelist[i],filter));
			}else{
				files.add(filelist[i]);
			}
		}
		Collections.sort(files,(new FilePathComparator()));
		return(files);
	}

	public final static ArrayList<File> getAllFilesRecursive(String path,FileFilter filter){
		File f=new File(path);
		return(getAllFilesRecursive(f,filter));
	}
	
	//recursively build a list of Files in a directory based on a list of FileFilters, sort the resulting list
	public final static ArrayList<File> getAllFilesRecursive(File path,ArrayList<FileFilter> filters){
		ArrayList<File> files=new ArrayList<File>();
		ArrayList<File> fileList=new ArrayList<File>();
		for(FileFilter filter:filters){
			fileList.addAll(Arrays.asList(path.listFiles(filter)));
		}
		for(File f:fileList) {
			if(f.isDirectory()){
				files.addAll(getAllFilesRecursive(f,filters));
			}else{
				files.add(f);
			}
		}
		Collections.sort(files,(new FilePathComparator()));
		return(files);
	}
	
    public static boolean compareFiles(File originalFile,File compareFile){
        /* compare file lengths first */
        long fileLength=originalFile.length();
        if(fileLength!=compareFile.length()){
            return(false);
        }else{
            boolean verified=true;
            FileInputStream fInOriginal=null;
            FileInputStream fInCompare=null;
            try{
                fInOriginal=new FileInputStream(originalFile);
                fInCompare=new FileInputStream(compareFile);
                int originalByte=fInOriginal.read();
                int compareByte=fInCompare.read();
                while((originalByte==compareByte)&&(originalByte!=-1)&&verified){
                    originalByte=fInOriginal.read();
                    compareByte=fInCompare.read();
                    verified=originalByte==compareByte;
                }
            }catch(Exception x){
            	x.printStackTrace();
            	verified=false;
            }finally{
                try{if(fInOriginal!=null){fInOriginal.close();}}catch(Exception x){x.printStackTrace();}
                try{if(fInCompare!=null){fInCompare.close();}}catch(Exception x){x.printStackTrace();}
            }
            return(verified);
        }
    }

	public static byte[] readBytes(String path,int startByte,int length) throws IOException{
		File f=new File(path);
		return(readBytes(f,startByte,length));
	}

	public static String readString(String path) throws IOException{
		File f=new File(path);
		return(readString(f));
	}
	
	public static String readString(File f) throws IOException{
		return(new String(readBytes(f,0,(int)f.length())));
	}
	
	public static byte[] readBytes(String path) throws IOException{
		File f=new File(path);
		return(readBytes(f,0,(int)f.length()));
	}
	
	private static byte[] readBytes(File f,int startByte,int length) throws IOException{
		FileInputStream fis=new FileInputStream(f);
		byte[] b=new byte[length];
		fis.skip(startByte);
		fis.read(b,0,length);
		fis.close();
		return(b);		
	}
	
	public static ArrayList<String> readLines(File f) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(f));
		ArrayList<String> lines=new ArrayList<String>();
		String line;
		while((line=br.readLine())!=null){
			lines.add(line);
		}
		br.close();		
		return(lines);
	}	
	
	public static ArrayList<String> readLines(String path) throws IOException{
		File f=new File(path);
		return(readLines(f));
	}

	//readLines but sorting the results
	public static ArrayList<String> readLines(String path,Comparator<String> sortComparator) throws IOException{
		ArrayList<String> linesSorted=readLines(path);
		linesSorted.sort(sortComparator);
		return(linesSorted);
	}	
	
	public static ArrayList<String> readLines(URL url) throws IOException{
		ArrayList<String> lines=new ArrayList<String>();
        URLConnection connection=url.openConnection();
        InputStreamReader in=new InputStreamReader(connection.getInputStream());
        BufferedReader br=new BufferedReader(in);
        String line;
        while((line=br.readLine())!=null){
        	lines.add(line);
        }
        br.close();
        return(lines);
	}

	//readLines but sorting the results
	public static ArrayList<String> readLines(URL url,Comparator<String> sortComparator) throws IOException{
		ArrayList<String> linesSorted=readLines(url);
		linesSorted.sort(sortComparator);
		return(linesSorted);
	}		
	
	public static String getExtension(String path){
		int lastIndexOf=path.lastIndexOf('.');
		if(lastIndexOf>0){
			//yes, this returns the .
			return(path.substring(lastIndexOf));
		}
		//the way this method is used, the right default is an empty string
		return("");
	}
	
	//these next two are more like "mkdirs if needed"
	public static void mkdirs(File f) throws Exception{
		if(!f.exists()){
			if(f.isDirectory()){
				f.mkdirs();
			}else{
				File p=f.getParentFile();
				if(!p.exists()){
					p.mkdirs();
				}
			}
		}
	}	
	
	public static void mkdirs(String path) throws Exception{
		File f=new File(path);
		mkdirs(f);
	}
	
	public static File getTempFile(String basePath){
		File f=(new File(basePath+(UUID.randomUUID().toString().replace("-",""))+".tmp"));
		while(f.exists()){//extremely small chance of this happening
			f=(new File(basePath+(UUID.randomUUID().toString().replace("-",""))+".tmp"));
		}
		return(f);
	}
	
	public static String buildUUIDName(File f,boolean preserveExtension){
		String currentFileName=f.getName();
		String newFileName=UUID.randomUUID().toString().replace("-","");
		if(preserveExtension){
			newFileName=newFileName+getExtension(currentFileName);
		}
		return(newFileName);
	}
	
	/* this is one of the most dangerous pieces of code I've written
	 * it overwrites all file names in a directory as a UUID
	 * 
	 * I mostly use this for mp3 player SD cards because:
	 * -mp3 speaker devices don't shuffle well if at all
	 * -sometimes long file names trip up mp3 speaker devices
	 * -and the same but for digital photo frames or the like
	 * 
	 * returns a map of the old & new file names in case of regrets
	 * if dryRun==true then no files are renamed but the map is populated
	 * 
	 * this is not recursive as a way to prevent myself from doing something really catastrophic
	 * 
	 * this does not work on MacOS if there are files with any extended characters on a FAT drive.. like many non-English languages
	 */
	public static Map<String,String> uuidRenamer(String path,FileFilter filter,boolean preserveExtension,boolean dryRun) throws Exception{
		Map<String,String> renameMap=new HashMap<String,String>();
		File dir=new File(path);
		File[] files=dir.listFiles(filter);
		for(File file:files){
			String currentFileName=file.getName();
			String newFileName=buildUUIDName(file,preserveExtension);
			renameMap.put(currentFileName,newFileName);
			if(!dryRun){
				File newFile=new File(path+newFileName);
				if(!file.renameTo(newFile)){
					throw(new Exception("Exception trying to rename ["+currentFileName+"] to ["+newFileName+"]"));
				}
			}
		}
		return(renameMap);
	}
	
	/*
	 * This is similar to the previous method but:
	 * 1) Slightly safer
	 * 2) Address an issue with the previous on MacOS when uuidRenameName is true
	 * 3) Really what I should have implemented in the first place
	 * 
	 * This was created to copy mp3s in a random order to an SD card.
	 * It works by:
	 * 1) Taking a list of file paths (sourceFileList) and choosing items randomly.
	 * 2) It checks if there is enough space at destinationPath to copy the file.
	 * 3) If there is enough space it copies the file.
	 * 4) It continues until all files are copied or destinationPath is out of space. 
	 * 5) The maxBytesKillswitch allows setting a maximum number of total bytes to copy.
	 *    Use this if you are prone to accidentally setting destinationPath to your hard drive.
	 *    I have not done this but am certainly capable of it.
	 * 
	 * Returns a map of which files were copied and what the destination name is.
	 */
	public static Map<String,String> shuffleCopy(List<String> sourceFileList,String destinationPath,boolean uuidRename,long maxBytesKillswitch){
		Map<String,String> copyMap=new HashMap<String,String>();
		if(sourceFileList==null){
			return(copyMap);
		}
		if(!destinationPath.endsWith(File.separator)){
			destinationPath=destinationPath+File.separator;
		}
		File destinationRoot=new File(destinationPath);
		if(!destinationRoot.exists()){
			return(copyMap);
		}
		int filesRemaining=sourceFileList.size();
		Random r=new Random();
		while((filesRemaining>0)&&(maxBytesKillswitch>0)){
			int index=r.nextInt(filesRemaining);
			String filePath=sourceFileList.remove(index);
			filesRemaining--;
			//is there room for this file?
			File source=new File(filePath);
			long sourceSize=source.length();
			long usableSpace=destinationRoot.getUsableSpace();//yes, need to check this each time in case of concurrent operations
			if(sourceSize<usableSpace){
				try{
					String fileName;
					if(uuidRename){
						fileName=buildUUIDName(source,true);
					}else{
						fileName=source.getName();
					}
					File destination=new File(destinationPath+File.separator+fileName);
					Files.copy(source.toPath(),destination.toPath(),StandardCopyOption.COPY_ATTRIBUTES);
					copyMap.put(filePath,destination.getPath());
					maxBytesKillswitch-=sourceSize;
				}catch(IOException iox){
					/*
					 * I checked the Java source code for this one...
					 * This error, from what I can tell, is platform independent and not localized.
					 * So this is 'safe' to check for. 
					 */
					if(iox.getMessage().toLowerCase().contains("no space left on device")){
						filesRemaining=-1;
					}
				}
			}			
		}		
		return(copyMap);
	}
}