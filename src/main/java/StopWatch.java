
public final class StopWatch {

	private long start_ = 0;
	private long stop_ = 0;

	void start() {
		this.start_ = System.nanoTime();
	//	this.start_= System.currentTimeMillis();
	}

	void stop() {
		this.stop_ = System.nanoTime();
//		this.stop_ = System.currentTimeMillis();
	}

	long elapsed_ns() {
		return stop_ - start_;
	}
	long elapsed_ms() {
		return (stop_ - start_)/1000000;
	}

//	double mps(long numOp) { // million per seconds
//		double et = elapsed_ms();
//		return (numOp / et);
//	}
	double mps(long numOp) { // million per seconds
		double et = elapsed_ns();
		return (numOp / et) * 1000.;
	}

}
