/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.example;

import java.util.Arrays;

/*
 * There's already a perfectly good way to sort a list of things in Java.
 * These are for interviewers who like re-inventing the wheel.
 * Maybe I'll do these in 68k assembly too for fun one day.  
 */
public abstract class SortAlgorithms{

	public static void insertionSort(int[] arrayToSort){
		int l=arrayToSort.length;
		for(int testIndex=1;testIndex<l;testIndex++){
			int key=arrayToSort[testIndex];
			int prevIndex=testIndex-1;
			while((prevIndex>=0)&&(arrayToSort[prevIndex]>key)){
				arrayToSort[prevIndex+1]=arrayToSort[prevIndex];
				prevIndex--;
			}
			arrayToSort[prevIndex+1]=key;
		}
	}

	public static void mergeSort(int[] arrayToSort){
		int[] workingArray=Arrays.copyOf(arrayToSort,arrayToSort.length);
		mergeSortRecursive(arrayToSort,0,arrayToSort.length,workingArray);
	}
	
	private static void mergeSortRecursive(int[] arrayToSort,int minIndex,int maxIndex,int[] workingArray){
	    if(minIndex+1>=maxIndex) {return;}
	    int midIndex=(maxIndex+minIndex)/2;              
	    mergeSortRecursive(workingArray,minIndex,midIndex,arrayToSort);  
	    mergeSortRecursive(workingArray,midIndex,maxIndex,arrayToSort);  
	    merge(arrayToSort,minIndex,midIndex,maxIndex,workingArray);
	}
	
	private static void merge(int[] arrayToSort,int minIndex,int midIndex,int maxIndex,int[] workingArray){
	   int min=minIndex;
	   int mid=midIndex;
	   for(int i=minIndex;i<maxIndex;i++){
		   if((min<midIndex)&&(mid>=maxIndex||(workingArray[min]<=workingArray[mid]))){
			   arrayToSort[i]=workingArray[min];
			   min++;
		   }else{
			   arrayToSort[i]=workingArray[mid];
			   mid++;
		   }
	   }
	}
}