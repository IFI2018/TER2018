
public final class EvalCoup {
	int [] val_;
	int [] coup_;

	EvalCoup(){
		val_=new int[6];
		coup_=new int[6];
	}
	void print(){
		for(int i=0;i<6;i++){
			System.out.println("coup: " + coup_[i] + " eval: " + val_[i]);
		}
	}
}
