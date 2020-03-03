package engine.util;

public class Timing {

	
	/**
	 * An accurate sync method
	 *
	 * Since Thread.sleep() isn't 100% accurate, we assume that it has
	 * roughly a margin of error of 1ms. This method will sleep for the
	 * sync time but burn a few CPU cycles "Thread.yield()" for the last
	 * 1 millisecond plus any remainder micro + nano's to ensure accurate
	 * sync time.
	 *
	 * @param fps The desired frame rate, in frames per second
	 */
	public static void sync(int fps, boolean vsync) {
	    if (fps <= 0 || vsync) return;
	     
	    long errorMargin = 1000*1000; // 1 millisecond error margin for Thread.sleep()
	    long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
	     
	    // if smaller than sleepTime burn for errorMargin + remainder micro & nano seconds
	    long burnTime = Math.min(sleepTime, errorMargin + sleepTime % (1000*1000));
	     
	    long overSleep = 0; // time the sleep or burn goes over by
	     
	    try {
	        while (true) {
	            long t = getTime() - lastTime;
	             
	            if (t < sleepTime - burnTime) {
	                Thread.sleep(1);
	            }
	            else if (t < sleepTime) {
	                // burn the last few CPU cycles to ensure accuracy
	                Thread.yield();
	            }
	            else {
	                overSleep = Math.min(t - sleepTime, errorMargin);
	                break; // exit while loop
	            }
	        }
	    } catch (InterruptedException e) {}
	     
	    lastTime = getTime() - overSleep;
	}
	
	public static long lastTime = 0;
	 
	/**
	 * Get System Nano Time
	 * @return will return the current time in nano's
	 */
	public static long getTime() {
	    return System.nanoTime();
	}
	
	public static double getTimeMS() {
		return System.nanoTime()/1e6f;
	}
	
}
