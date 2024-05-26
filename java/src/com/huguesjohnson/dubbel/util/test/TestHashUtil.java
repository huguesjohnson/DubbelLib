/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.HashUtil;


class TestHashUtil{

	@Test
	public void test_computeHashSHA3512(){
		try{
			assertEquals(HashUtil.computeHashSHA3512(""),"a69f73cca23a9ac5c8b567dc185a756e97c982164fe25859e0d1dcc1475c80a615b2123af1f5f94c11e3e9402c3ac558f500199d95b6d3e301758586281dcd26");
			assertEquals(HashUtil.computeHashSHA3512("I like pie"),"c203ba331a876b2e1d6b9d174d54b82dd29317cd0e132675e7a037387311edaa8ee0ed210849f5f5100744a115bdbb5fe7b04c4ffaf28ec42df1a239fa452372");
			assertEquals(HashUtil.computeHashSHA3512("correct horse battery staple"),"4b65d7b7acc886f9add07db3a5d42bf0032fe0109a1fd56f623c7093e8a59689f9246918a4f388034ddf393231eaba0742b3dc1840e4556270a729ce56098f35");
			assertEquals(HashUtil.computeHashSHA3512("Please no more California songs"),"39b1aa8b65cb7387f0e47942c8997925f72c81882878aac63b4f9270f6ee23829ee2dcc69d3827fa5b6e1c1cf69696a4ab10e46106fb87730d06ce1d5ba01790");
		}catch(NoSuchAlgorithmException x){
			fail(x.getMessage());
		}
	}
	
	@Test
	public void test_computeHashMD5(){
		try{
			assertEquals(HashUtil.computeHashMD5(""),"d41d8cd98f00b204e9800998ecf8427e");
			assertEquals(HashUtil.computeHashMD5("I like pie"),"39d74f597a854b15819d46422e26879a");
			assertEquals(HashUtil.computeHashMD5("correct horse battery staple"),"9cc2ae8a1ba7a93da39b46fc1019c481");
			assertEquals(HashUtil.computeHashMD5("Please no more California songs"),"ae39078c1789b06135f52c43bd878be3");
		}catch(NoSuchAlgorithmException x){
			fail(x.getMessage());
		}
	}
	
}