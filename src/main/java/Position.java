
public final class Position {
	static final int cnext_[][]={{0,0,1,2,3,4},{1,2,3,4,5,5}};
	static final int jnext_[][]={{1,0,0,0,0,0},{1,1,1,1,1,0}};

	private final int[][] cases_; // 6 cases et 2 joueurs
	private final int[] pionsPris_; // 2 joueurs

	
//	private int [] cases0_;
//	private int [] cases1_;
	
	public int[] getPionsPris_() {
		return pionsPris_;
	}

	Position() {
		cases_ = new int[2][6];
		pionsPris_ = new int[2];
		/*
		 * useless in Java for(int i=0;i<6;i++){ cases_[0][i]=0; cases_[1][i]=0;
		 * } pionsPris_[0]=pionsPris_[1]=0;
		 */
		//cases0_=cases_[0];
		//cases1_=cases_[1];
	}
	// Ordre interne
	// aiguille d'une montre :
	// j=0 5 4 3 2 1 O
	// j=1 5 4 3 2 1 0
	// voir tableau des next
	
	/// <summary> Position a partir de laquelle on commence à jouer </summary>
	int[] cases_0(){return cases_[0];}
	int[] cases_1(){return cases_[1];}
	
	int somme_pions_pris(){
		return pionsPris_[0] + pionsPris_[1];
	}
	int pions_pris_0(){return pionsPris_[0];}
	int pions_pris_1(){return pionsPris_[1];}
	void position_debut() {
		for (int i = 0; i < 6; i++) {
			cases_[0][i] = 4;
			cases_[1][i] = 4;
		}
		pionsPris_[0] = pionsPris_[1] = 0;

		/*
		 * pos.cases_[1][5]=6; pos.cases_[1][4]=0; pos.cases_[1][3]=0;
		 * pos.cases_[1][2]=0; pos.cases_[1][1]=1; pos.cases_[1][0]=0;
		 * pos.cases_[0][5]=1; pos.cases_[0][4]=0; pos.cases_[0][3]=0;
		 * pos.cases_[0][2]=0; pos.cases_[0][1]=0; pos.cases_[0][0]=0;
		 * pos.pionsPris_[1]=17; pos.pionsPris_[0]=23;
		 */

	}

	void print_position() {
		System.out.println("--------------------------");
		for (int i = 5; i >= 0; i--) {
			System.out.print("[" + cases_[0][i] + "] ");
		}
		System.out.println(" PP=" + pionsPris_[0]);
		for (int i = 5; i >= 0; i--) {
			System.out.print("[" + cases_[1][i] + "] ");
		}
		System.out.println(" PP=" + pionsPris_[1]);
	}

	void print_position_ordi_bas_inv() {
		System.out.println("--------------------------");
		for (int i = 0; i < 6; i++) {
			System.out.print("[" + cases_[1][i] + "] ");
		}
		System.out.print(" PP=" + pionsPris_[1]);
		for (int i = 0; i < 6; i++) {
			System.out.print("[" + cases_[0][i] + "] ");
		}
		System.out.println(" PP=" + pionsPris_[0] + "\n--------------------------");
	}

	void print_position_ordi_haut_inv() {
		System.out.println("--------------------------");
		for (int i = 5; i >= 0; i--) {
			System.out.print("[" + cases_[0][i] + "] ");
		}
		System.out.println(" PP=" + pionsPris_[0]);
		for (int i = 5; i >= 0; i--) {
			System.out.print("[" + cases_[1][i] + "] ");
		}
		System.out.println(" PP=" + pionsPris_[1] + "\n--------------------------");
	}

	// Affichage
	// 12 11 10 9 8 7
	// 1 2 3 4 5 6
	// on tourne dans le sens inverse des aiguilles d'une montre

	void print_position_ordi_bas() {
		System.out.println("--------------------------");
		for (int i = 5; i >= 0; i--) {
			System.out.print("[" + cases_[1][i] + "] ");
		}
		System.out.println(" PP=" + pionsPris_[1]);
		for (int i = 5; i >= 0; i--) {
			System.out.print("[" + cases_[0][i] + "] ");
		}
		System.out.print(" PP=" + pionsPris_[0] + "\n--------------------------");
	}

	void print_position_ordi_haut() {
		System.out.println("--------------------------");
		for (int i = 0; i < 6; i++) {
			System.out.print("[" + cases_[0][i] + "] ");
		}
		System.out.println(" PP=" + pionsPris_[0]);
		for (int i = 0; i < 6; i++) {
			System.out.print("[" + cases_[1][i] + "] ");
		}
		System.out.println(" PP=" + pionsPris_[1] + "\n--------------------------");
	}

	private int somme_0(){
		final int[] cases=cases_[0];
		return cases[0] + cases[1] + cases[2] + cases[3] + cases[4] + cases[5];
	}
	private int somme_1(){
		final int[] cases=cases_[1];
		return cases[0] + cases[1] + cases[2] + cases[3] + cases[4] + cases[5];
	}
	private int somme(final int joueur){
		int[] casesj=cases_[joueur];
		return casesj[0] + casesj[1] + casesj[2] + casesj[3] + casesj[4] + casesj[5];		
	}
	boolean est_affame(int joueur) {
		//return cases_[joueur][0] + cases_[joueur][1] + cases_[joueur][2] + cases_[joueur][3] + cases_[joueur][4] + cases_[joueur][5] == 0;
		int[] casesj=cases_[joueur];
		for (int i = 0; i < 6; i++) {
			if (casesj[i] > 0) return false;
		}
		return true;
		
	}
	boolean non_affame(int joueur) {
		//return cases_[joueur][0] + cases_[joueur][1] + cases_[joueur][2] + cases_[joueur][3] + cases_[joueur][4] + cases_[joueur][5] == 0;
		int[] casesj=cases_[joueur];
		for (int i = 0; i < 6; i++) {
			if (casesj[i] > 0) return true;
		}
		return false;
		
	}

	void fill(final Position pos){
		int[] cases=cases_[0];
		int[] poscases=pos.cases_[0];
		for (int i=0;i<6;i++){
			cases[i] = poscases[i];			
		}
		cases=cases_[1];
		poscases=pos.cases_[1];
		for (int i=0;i<6;i++){
			cases[i] = poscases[i];
		}
		pionsPris_[0] = pos.pionsPris_[0];
		pionsPris_[1] = pos.pionsPris_[1];
	}
	void copier(Position newPos) {
		//System.arraycopy(cases_[0], 0, newPos.cases_[0], 0, cases_[0].length);
		//System.arraycopy(cases_[1], 0, newPos.cases_[1], 0, cases_[1].length);
		//System.arraycopy(pionsPris_, 0, newPos.pionsPris_, 0, pionsPris_.length);
		int[] cases=cases_[0];
		int[] newcases=newPos.cases_[0];
		for (int i=0;i<6;i++){
			newcases[i] = cases[i];			
		}
		cases=cases_[1];
		newcases=newPos.cases_[1];
		for (int i=0;i<6;i++){
			newcases[i] = cases[i];
		}
		newPos.pionsPris_[0] = pionsPris_[0];
		newPos.pionsPris_[1] = pionsPris_[1];
		/*
		for (int i = 0; i < 6; i++) {
			newPos.cases_[0][i] = cases_[0][i];
			newPos.cases_[1][i] = cases_[1][i];
		}
		newPos.pionsPris_[0] = pionsPris_[0];
		newPos.pionsPris_[1] = pionsPris_[1];
		*/
	}
/*	private void copier_internal(Position newPos) {
		for (int i=0;i<6;i++){
			newPos.cases0_[i] = cases0_[i];			
		}
		for (int i=0;i<6;i++){
			newPos.cases1_[i] = cases1_[i];
		}
		newPos.pionsPris_[0] = pionsPris_[0];
		newPos.pionsPris_[1] = pionsPris_[1];
	}
*/
	private void copier_internal(Position newPos) {
		//System.arraycopy(cases_[0], 0, newPos.cases_[0], 0, cases_[0].length);
		//System.arraycopy(cases_[1], 0, newPos.cases_[1], 0, cases_[1].length);
		//System.arraycopy(pionsPris_, 0, newPos.pionsPris_, 0, pionsPris_.length);
		int[] cases=cases_[0];
		int[] newcases=newPos.cases_[0];
		for (int i=0;i<6;i++){
			newcases[i] = cases[i];			
		}
		cases=cases_[1];
		newcases=newPos.cases_[1];
		for (int i=0;i<6;i++){
			newcases[i] = cases[i];
		}
		newPos.pionsPris_[0] = pionsPris_[0];
		newPos.pionsPris_[1] = pionsPris_[1];
	}

	int evaluer() {
		return pionsPris_[0] - pionsPris_[1];
	}

	boolean test_fin() {
		if (pionsPris_[0] + pionsPris_[1] > 24) {
			if (48 - pionsPris_[0] - pionsPris_[1] <= 6) {
				return true;
			}
			if (pionsPris_[0] >= 25)
				return true;
			if (pionsPris_[1] >= 25)
				return true;
		}
		return false;
	}
	// private change quelque chose ???
	private void jouer_coup_internal_0(final int coup) {
		int j = 0;
		int c = coup;
		final int cases1[]=cases_[1];
		final int cases0[]=cases_[0];
		do {
			// on distribue ces pions
			final int nbp = cases0[coup];
			cases0[coup] = 0;
			for (int i = 0; i < nbp; i++) {
				final int tj = j;
				j = jnext_[j][c];
				c = cnext_[tj][c];
				cases_[j][c]++;
			}
		}while (cases0[coup] != 0);
		if (j ==1) {// on regarde si on doit prendre des pions
			for (int i = c; i >= 0; i--) {
				if (cases1[i] == 2 || cases1[i] == 3) {
					pionsPris_[0] += cases1[i];
					cases1[i] = 0;
				} else {
					break;
				}
			}
		}
	}
	private void jouer_coup_internal_1(final int coup) {
		int j = 1;
		int c = coup;
		final int cases1[]=cases_[1];
		final int cases0[]=cases_[0];
		do {
			// on distribue ces pions
			final int nbp = cases1[coup];
			cases1[coup] = 0;
			for (int i = 0; i < nbp; i++) {
				final int tj = j;
				j = jnext_[j][c];
				c = cnext_[tj][c];
				cases_[j][c]++;
			}
		}while (cases1[coup] != 0);
		
		if (j ==0) {// on regarde si on doit prendre des pions
				for (int i = c; i <= 5; i++) {
					if (cases0[i] == 2 || cases0[i] == 3) {
						pionsPris_[1] += cases0[i];
						cases0[i] = 0;
					} else {
						break;
					}
				}
		}
	}
	void jouer_coup_internal(final int joueur, final int coup) {
		//final int nbpions = cases_[joueur][coup];
		int j = joueur;
		int c = coup;
/*		cases_[joueur][coup] = 0;
		for (int i = 0; i < nbpions; i++) {
			final int tj = j;
			j = jnext_[j][c];
			c = cnext_[tj][c];
			cases_[j][c]++;
		}*/
		// on regarde si la case de départ est vide ou pas.
		
		do {
			// on distribue ces pions
			final int nbp = cases_[joueur][coup];
			cases_[joueur][coup] = 0;
			for (int i = 0; i < nbp; i++) {
				final int tj = j;
				j = jnext_[j][c];
				c = cnext_[tj][c];
				cases_[j][c]++;
			}
		}while (cases_[joueur][coup] != 0);
		
/*		int nbp = cases_[joueur][coup];
		while (nbp != 0) {
			// on distribue ces pions
			cases_[joueur][coup] = 0;
			for (int i = 0; i < nbp; i++) {
				final int tj = j;
				j = jnext_[j][c];
				c = cnext_[tj][c];
				cases_[j][c]++;
			}
			nbp = cases_[joueur][coup];
		}
	*/	
		if (j != joueur) {// on regarde si on doit prendre des pions
			if (j == 0) {
				for (int i = c; i <= 5; i++) {
					if (cases_[j][i] == 2 || cases_[j][i] == 3) {
						pionsPris_[joueur] += cases_[j][i];
						cases_[j][i] = 0;
					} else {
						break;
					}
				}
			} else {
				for (int i = c; i >= 0; i--) {
					if (cases_[j][i] == 2 || cases_[j][i] == 3) {
						pionsPris_[joueur] += cases_[j][i];
						cases_[j][i] = 0;
					} else {
						break;
					}
				}
			}
		}
	}
	boolean jouer_coup(Position newPos, final int joueur, final int coup) {
		// retourne 1 si le coup est jouable et 0 sinon
		if (cases_[joueur][coup] == 0) {
			return false; // le cout n'est pas jouable
		}
		// on met a 0 la structure
		copier(newPos);
		newPos.jouer_coup_internal(joueur, coup);
		// on regarde si l'adversaire est affame
		return newPos.somme((joueur+1)%2)>0;
	}
	boolean jouer_coup_0(Position newPos, final int coup) {
		// retourne 1 si le coup est jouable et 0 sinon
		//if (cases_[0][coup] == 0) {
		//	return false; // le cout n'est pas jouable
		//}
		// on met a 0 la structure
		copier_internal(newPos);
		//newPos.fill(this);
		newPos.jouer_coup_internal_0(coup);
		// on regarde si l'adversaire est affame
		return newPos.somme_1()>0;
		//return newPos.non_affame(1);
	}
	boolean jouer_coup_1(Position newPos, final int coup) {
		// retourne 1 si le coup est jouable et 0 sinon
		//if (cases_[1][coup] == 0) {
		//	return false; // le cout n'est pas jouable
		//}
		// on met a 0 la structure
		copier_internal(newPos);
		newPos.jouer_coup_internal_1(coup);
		// on regarde si l'adversaire est affame
		return newPos.somme_0()>0;
	}
	
/*	boolean jouer_coup(Position newPos, final int joueur, final int coup) {
		// retourne 1 si le coup est jouable et 0 sinon
		final int nbpions = cases_[joueur][coup];
		if (nbpions == 0) {
			return false; // le cout n'est pas jouable
		}
		// on met a 0 la structure
		copier(newPos);
		newPos.cases_[joueur][coup] = 0;
		int j = joueur;
		int c = coup;
		for (int i = 0; i < nbpions; i++) {
			final int tj = j;
			j = jnext_[j][c];
			c = cnext_[tj][c];
			newPos.cases_[j][c]++;
		}
		// on regarde si la case de départ est vide ou pas.
		int nbp = newPos.cases_[joueur][coup];
		while (nbp != 0) {
			// on distribue ces pions
			newPos.cases_[joueur][coup] = 0;
			for (int i = 0; i < nbp; i++) {
				final int tj = j;
				j = jnext_[j][c];
				c = cnext_[tj][c];
				newPos.cases_[j][c]++;
			}
			nbp = newPos.cases_[joueur][coup];
		}
		if (j != joueur) {// on regarde si on doit prendre des pions
			if (j == 0) {
				for (int i = c; i <= 5; i++) {
					if (newPos.cases_[j][i] == 2 || newPos.cases_[j][i] == 3) {
						newPos.pionsPris_[joueur] += newPos.cases_[j][i];
						newPos.cases_[j][i] = 0;
					} else {
						break;
					}
				}
			} else {
				for (int i = c; i >= 0; i--) {
					if (newPos.cases_[j][i] == 2 || newPos.cases_[j][i] == 3) {
						newPos.pionsPris_[joueur] += newPos.cases_[j][i];
						newPos.cases_[j][i] = 0;
					} else {
						break;
					}
				}
			}
		}
		// on regarde si l'adversaire est affame
		return newPos.somme((joueur+1)%2)>0;
		// remarque: ne change rien
//		if (newPos.est_affame((joueur + 1) % 2))
//			return false;
//		return true;
	}
	*/
	/*
	boolean jouer_coup(final int joueur, final int coup) {
		// retourne 1 si le coup est jouable et 0 sinon
		final int nbpions = cases_[joueur][coup];
		if (nbpions == 0) {
			return false; // le cout n'est pas jouable
		}
		// on met a 0 la structure
		cases_[joueur][coup] = 0;
		int j = joueur;
		int c = coup;
		for (int i = 0; i < nbpions; i++) {
			final int tj = j;
			j = jnext_[j][c];
			c = cnext_[tj][c];
			cases_[j][c]++;
		}
		// on regarde si la case de départ est vide ou pas.
		int nbp = cases_[joueur][coup];
		while (nbp != 0) {
			// on distribue ces pions
			cases_[joueur][coup] = 0;
			for (int i = 0; i < nbp; i++) {
				final int tj = j;
				j = jnext_[j][c];
				c = cnext_[tj][c];
				cases_[j][c]++;
			}
			nbp = cases_[joueur][coup];
		}
		if (j != joueur) {// on regarde si on doit prendre des pions
			if (j == 0) {
				for (int i = c; i <= 5; i++) {
					if (cases_[j][i] == 2 || cases_[j][i] == 3) {
						pionsPris_[joueur] += cases_[j][i];
						cases_[j][i] = 0;
					} else {
						break;
					}
				}
			} else {
				for (int i = c; i >= 0; i--) {
					if (cases_[j][i] == 2 || cases_[j][i] == 3) {
						pionsPris_[joueur] += cases_[j][i];
						cases_[j][i] = 0;
					} else {
						break;
					}
				}
			}
		}
		// on regarde si l'adversaire est affame
		return somme((joueur+1)%2)>0;
		// remarque: ne change rien
//		if (newPos.est_affame((joueur + 1) % 2))
//			return false;
//		return true;
	}
	*/
}
