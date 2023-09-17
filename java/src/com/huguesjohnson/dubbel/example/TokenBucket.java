/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.example;

/* 
 * Example token bucket.
 * This came up on an actual interview once.
 * It wasn't even for a coding position.
 */
public class TokenBucket{
	//the last time the bucket was re-filled (ms)
	private long lastFillTime;
	//the number of tokens available
	private int availableTokens;
	//the refill interval (ms)
	private final long refillInterval;
	//the maximum number of tokens
	private final int maxTokens;
	
	public TokenBucket(int maxTokens,long refillInterval) throws IllegalArgumentException{
		if(maxTokens<=0){
			throw(new IllegalArgumentException("maxTokens must be > 0"));
		}
		if(refillInterval<=0L){
			throw(new IllegalArgumentException("refillInterval must be > 0"));
		}
		this.maxTokens=maxTokens;
		this.availableTokens=maxTokens;
		this.refillInterval=refillInterval;
		this.lastFillTime=System.currentTimeMillis();
	}

	public int getTokens(int numTokensRequested){
		this.checkRefill();
		//could also throw an exception here
		if(numTokensRequested<=0){return(0);}
		if(numTokensRequested>=this.availableTokens){
			int tokensReturned=this.availableTokens;
			this.availableTokens=0;
			return(tokensReturned);
		}
		//numTokensRequested<this.availableTokens
		this.availableTokens-=numTokensRequested;
		return(numTokensRequested);
	}
	
	public boolean hasAvailableTokens(){
		this.checkRefill();
		return(this.availableTokens>0);
	}
	
	private void checkRefill(){
		long currentTime=System.currentTimeMillis();
		long delta=currentTime-this.lastFillTime;
		if(delta>=this.refillInterval){
			this.lastFillTime=currentTime;
			this.availableTokens=this.maxTokens;
		}
	}
}