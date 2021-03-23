public class RateLimit {
    public static void main(String[] args) throws InterruptedException {
        //create Semaphore for rate limit
        Semaphore sp = new Semaphore(2);
        
        //generating 4 threads (A, B, C, D) in every second, loop 10 times
        for (int i = 0; i < 10; i++) {
        	Thread.sleep(1000);
        	new Thread(() -> {
        		try {
        			sp.acquire();
        			doDBQuery(Thread.currentThread().getName());
        		} 
        		catch (InterruptedException e) {e.printStackTrace();}
        		finally {
        			finishDBQuery(Thread.currentThread().getName(), sp);
		        }
        	}, "A"+i).start();
        	
        	new Thread(() -> {
        		try {
        			sp.acquire();
        			doDBQuery(Thread.currentThread().getName());
        		} 
        		catch (InterruptedException e) {e.printStackTrace();}
        		finally {
        			finishDBQuery(Thread.currentThread().getName(), sp);
		        }
        	}, "B"+i).start();
        	new Thread(() -> {
        		try {
        			sp.acquire();
        			doDBQuery(Thread.currentThread().getName());
        		} 
        		catch (InterruptedException e) {e.printStackTrace();}
        		finally {
        			finishDBQuery(Thread.currentThread().getName(), sp);
		        }
        	}, "C"+i).start();
        	new Thread(() -> {
        		try {
        			sp.acquire();
        			doDBQuery(Thread.currentThread().getName());
        		} 
        		catch (InterruptedException e) {e.printStackTrace();}
        		finally {
        			finishDBQuery(Thread.currentThread().getName(), sp);
		        }
        	}, "D"+i).start();
        }
    }
    //do DB query job
    public static void doDBQuery(String tName) throws InterruptedException {
    	Thread.sleep(500);
    	System.out.println(tName+" : select columns from tb");
    	Thread.sleep(500);
    }
    //finish release Semaphore
    public static void finishDBQuery(String tName, Semaphore sp) {
    	System.out.println(Thread.currentThread().getName()+" finish query job, release semaphore, there are "+sp.getQueueLength()+" job remained");
    	sp.release();
    }
}
