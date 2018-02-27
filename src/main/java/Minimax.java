import java.util.ArrayDeque;
import java.util.Scanner;
import org.openjdk.jmh.*;

// RMQ : static final ne change pas grand chose
// EValCOup ne change pas grand chose

public final class Minimax {
	// KKKprivate int[][] cnext_; // [2][6];
	// KKKprivate int[][] jnext_; // [2][6];

	// YYYprivate ArrayDeque<Position> positionPool_;
	private Position[] pool_;
	private int pmax_;
	private int numPool_;

	// int[] val_;
	// int[] coup_;

	static final int VALMAX = 48;
	int NUM_MINIMAX = 0;
	int VALMM = 0;

	boolean gagne_;

	Minimax() {
		// KKK cnext_ = new int[2][6];
		// KKK jnext_ = new int[2][6];
		pool_ = new Position[64];
		numPool_ = 64;
		// YYYpositionPool_ = new ArrayDeque<Position>(64);
		for (int i = 0; i < 64; i++) {
			Position p = new Position();
			pool_[i] = p;
			// YYYpositionPool_.push(p);
		}
		// val_ = new int[6];
		// coup_ = new int[6];
	}

	Position createPosition() {
		return pool_[--numPool_];
		// YYYreturn positionPool_.pop();
		// return new Position();
	}

	void releasePosition(Position p) {
		// void releasePosition(){
		// pool_[numPool_]=p;
		numPool_++;
		// YYYpositionPool_.push(p);
	}

	/*
	 * int calculer_coup(Position pos, final int joueur, int alpha, int beta) {
	 * Position newPos = createPosition(); //final int pmax=pmax_; if (pmax_
	 * ==1){ if (joueur == 0) {// MAX for (int i = 0; i < 6; i++) { if
	 * (pos.jouer_coup_0(newPos, i)) { final int val= newPos.evaluer(); if (val
	 * > alpha) { alpha = val; if (alpha >= beta) { break; } } } }
	 * releasePosition(newPos); return alpha; } // cout << "MIN"; for (int i =
	 * 0; i < 6; i++) { if (pos.jouer_coup_1(newPos, i)) { final int val=
	 * newPos.evaluer(); if (val < beta) { beta = val; if (beta <= alpha) {
	 * break; } } } // AAA } } releasePosition(newPos); return beta; } if
	 * (joueur == 0) {// MAX for (int i = 0; i < 6; i++) { if
	 * (pos.jouer_coup_0(newPos, i)) { pmax_--; final int val =
	 * valeur_minimaxAB(newPos, 1, alpha, beta); pmax_++; if (val > alpha) {
	 * alpha = val; if (alpha >= beta) { break; } } } } releasePosition(newPos);
	 * return alpha; } for (int i = 0; i < 6; i++) { if
	 * (pos.jouer_coup_1(newPos, i)) { pmax_--; final int val =
	 * valeur_minimaxAB(newPos,0, alpha, beta); pmax_++; if (val < beta) { beta
	 * = val; if (beta <= alpha) { // cout << "b"; break; } } } }
	 * releasePosition(newPos); return beta; }
	 */
	private int calculer_coup_0(final Position pos, int alpha, int beta) {
		final Position newPos = pool_[pmax_];
		pmax_--;
		final int[] cases0 = pos.cases_0();
		for (int i = 0; i < 6; i++) {
			if (cases0[i] > 0) {
				if (pos.jouer_coup_0(newPos, i)) {
					NUM_MINIMAX++;
					final int val=(pmax_==0) ? newPos.evaluer(): valeur_minimaxAB_0(newPos, alpha, beta);
					if (val > alpha) {
						alpha = val;
					}
					if (alpha >= beta) {
						break;
					}
				}
			}
		}
		pmax_++;
		return alpha;
	}

	private int calculer_coup_1(final Position pos, int alpha, int beta) {
		final Position newPos = pool_[pmax_--];
		//pmax_--;
		final int[] cases1 = pos.cases_1();
		for (int i = 0; i < 6; i++) {
			if (cases1[i] > 0) {
				if (pos.jouer_coup_1(newPos, i)) {
					NUM_MINIMAX++;
					final int val=(pmax_==0) ? newPos.evaluer(): valeur_minimaxAB_1(newPos, alpha, beta);
					if (val < beta) {
						beta = val;
					}
						if (beta <= alpha) {
							break;
						}
				}
			}
		}
		pmax_++;
		return beta;
	}
	private int valeur_minimaxAB_0(final Position pos, int alpha, int beta) {
		final int pp0 = pos.pions_pris_0();
		final int pp1 = pos.pions_pris_1();
		if (pp0 + pp1 > 24) {
			// On peut eviter ce test
			// on multiplie par gagane_
			final int ajoutProf = (gagne_) ? pmax_ : 0;
			if (48 - pp0 - pp1 <= 6) {
				if (pp0 > pp1) {
					return VALMAX + ajoutProf;
				}
				if (pp0 < pp1)
					return -VALMAX - ajoutProf;
				return 0; // partie nulle
			}
//			if (pp0 >= 25) {
//				return VALMAX + ajoutProf;
//			}
			if (pp1 >= 25)
				return -VALMAX - ajoutProf;

		}
		return calculer_coup_1(pos, alpha, beta);
	}
	private int valeur_minimaxAB_1(final Position pos, int alpha, int beta) {
		final int pp0 = pos.pions_pris_0();
		final int pp1 = pos.pions_pris_1();
		if (pp0 + pp1 > 24) {
			final int ajoutProf = (gagne_) ? pmax_ : 0;
			if (48 - pp0 - pp1 <= 6) {
				if (pp0 > pp1) {
					return VALMAX + ajoutProf;
				}
				if (pp0 < pp1)
					return -VALMAX - ajoutProf;
				return 0; // partie nulle
			}
			if (pp0 >= 25) {
				return VALMAX + ajoutProf;
			}
//			if (pp1 >= 25)
//				return -VALMAX - ajoutProf;

		}
		return calculer_coup_0(pos, alpha, beta);
	}

	private int calculer_coup(final Position pos, final int joueur, int alpha, int beta) {
		// Position newPos = createPosition();
		final Position newPos = pool_[pmax_--];
		// final int pmax=pmax_;
		//pmax_--;
		if (joueur == 0) {// MAX
			final int[] cases0 = pos.cases_0();
			for (int i = 0; i < 6; i++) {
				if (cases0[i] > 0) {
					if (pos.jouer_coup_0(newPos, i)) {
						final int val;
						if (pmax_ == 0) {
							val = newPos.evaluer();
						} else {
							// pmax_--;
							val = valeur_minimaxAB(newPos, 1, alpha, beta);
							// val = valeur_minimaxAB(newPos,1, alpha, beta);
							// pmax_++;
						}
						if (val > alpha) {
							alpha = val;
						}
							if (alpha >= beta) {
								break;
							}
					
					}
				}
			}
			// releasePosition();
			pmax_++;
			// releasePosition(newPos);
			return alpha;
		}
		// cout << "MIN";
		final int[] cases1 = pos.cases_1();
		for (int i = 0; i < 6; i++) {
			// AAA if (pos.cases_[joueur][i] > 0){
			// AAA pos.copier(newPos);
			// AAA if (newPos.jouer_coup(joueur, i)) {
			if (cases1[i] > 0) {
				if (pos.jouer_coup_1(newPos, i)) {
					final int val;
					if (pmax_ == 0) {
						val = newPos.evaluer();
					} else {
						// pmax_--;
						// final int val = valeur_minimaxAB(newPos,0, alpha,
						// beta);
						val = valeur_minimaxAB(newPos, 0, alpha, beta);
						// pmax_++;
					}
					// cout << " coup: " << i << " val: " << val << " alpha: "
					// <<
					// alpha << " beta:" << beta << endl;
					if (val < beta) {
						beta = val;
					}
						if (beta <= alpha) {
							// cout << "b";
							break;
						}
				
				}
			}
			// AAA }
		}
		// releasePosition();
		pmax_++;
		// releasePosition(newPos);
		return beta;
	}

	private int valeur_minimaxAB(final Position pos, final int joueur, int alpha, int beta) {
		// Calcule la valeur de e pour le joueur J selon que e EstUnEtatMax ou
		// pas et pmax la profondeur courante
		// comparaison 1 si a > b, 0 si = et -1 sinon : (a > b) - (a < b);
		// int ret=(int)(pos.pionsPris_[0] > pos.pionsPris_[1]) -
		// (int)(pos.pionsPris_[0] < pos.pionsPris_[1]);
		NUM_MINIMAX++;

		final int pp0 = pos.pions_pris_0();
		final int pp1 = pos.pions_pris_1();
		if (pp0 + pp1 > 24) {
			final int ajoutProf = (gagne_) ? pmax_ : 0;
			if (48 - pp0 - pp1 <= 6) {
				if (pp0 > pp1) {
					return VALMAX + ajoutProf;
				}
				if (pp0 < pp1)
					return -VALMAX - ajoutProf;
				return 0; // partie nulle
			}
			if (pp0 >= 25) {
				return VALMAX + ajoutProf;
			}
			if (pp1 >= 25)
				return -VALMAX - ajoutProf;

		}
		return calculer_coup(pos, joueur, alpha, beta);
	}

	int decisionAB(Position pos) {
		// on regarde le nombre de case vide et on ajoute de la profondeur
		// eventuellement
		// k case vide = profmax * k/12
		int k = 0;
		for (int i = 0; i < 6; i++) {
			if (pos.cases_0()[i] == 0)
				k++;
			// if (pos.cases_[1][i] == 0) k++;
		}
		// k--;
		if (k > 0) {
			if (pos.somme_pions_pris() >= 20)
				pmax_++;
			if (pos.somme_pions_pris() >= 25)
				pmax_++;

			if (k <= 3)
				pmax_++;
			if (k > 3)
				pmax_ += 2;
		}
		System.out.println("prof max: " + pmax_);

		int alpha = -VALMAX - 50; // avant -1
		int beta = VALMAX + 50; // avant +1
//		Position newPos = createPosition();
		Position newPos=pool_[pmax_];
		pmax_--;
		// pos.copier(newPos);
		int coup = 0;
		for (int i = 0; i < 6; i++) {
			// AAA if (pos.cases_[0][i] > 0){
			// AAA pos.copier(newPos);
			// AAA if (newPos.jouer_coup(0, i)) {
			if (pos.jouer_coup(newPos, 0, i)) {
//				NUM_MINIMAX++;
//				pmax_--;
//				final int val = valeur_minimaxAB(newPos, 1, alpha, beta);
				final int val = valeur_minimaxAB_0(newPos, alpha, beta);
//				pmax_++;
				if (val > alpha) {
					alpha = val;
					coup = i;
				}
			}
			// AAA }
		}
		pmax_++;

		VALMM = alpha;
		// releasePosition();
//		releasePosition(newPos);
		return coup;
	}

	void start_game() {

		Position pos = new Position();
		Position newPos = new Position();
		pos.position_debut();

		System.out.println("DEBUT DU JEU AWALE");
		System.out.println("(0) l'ordinateur commence");
		System.out.println("(1) le joueur commence");
		int joueur;
		Scanner sc = new Scanner(System.in);
		joueur = sc.nextInt();

		boolean ordiCommence = (joueur == 0);
		System.out.println("C'est parti!");

		boolean fin = false;
		gagne_ = false;

		StopWatch sw = new StopWatch();

		while (!fin) {
			int coup;
			if (joueur == 0) { // l'ordi JOUE
				sw.start();
				pmax_ = 17;
				coup = decisionAB(pos); // ATTENTION C'est LA
										// PROFONDEUR MAX 11
				sw.stop();
				System.out.println("time: " + sw.elapsed_ms());
				if (!gagne_ && VALMM == 48) {
					gagne_ = true;
				}
				System.out.println("Noeuds traites: " + NUM_MINIMAX + " valeur minimax:" + VALMM);
				int cj;
				if (ordiCommence) {
					cj = 6 - coup;
				} else {
					cj = 12 - coup;
				}
				System.out.println("COUP JOUE: " + cj);
				NUM_MINIMAX = 0;
				// AAA pos.jouer_coup(joueur, coup);

				pos.copier(newPos);
				pos.jouer_coup(newPos,0, coup);
//				pos.jouer_coup(newPos, joueur, coup);
				newPos.copier(pos);

				if (ordiCommence) {
					pos.print_position_ordi_haut_inv();
				} else {
					pos.print_position_ordi_bas_inv();
				}
			} else { // le JOUEUR JOUE
				if (ordiCommence) {
					System.out.println("selectionner une case 12 11 10 9 8 7");
					coup = sc.nextInt();
					coup -= 7;
				} else {
					System.out.println("selectionner une case 1 2 3 4 5 6");
					coup = sc.nextInt();
					// coup=6-coup;
					coup--;
				}
				// AAA pos.jouer_coup(joueur, coup);
				pos.copier(newPos);
				pos.jouer_coup(newPos, joueur, coup);
				newPos.copier(pos);
				if (ordiCommence) {
					pos.print_position_ordi_haut_inv();
				} else {
					pos.print_position_ordi_bas_inv();
				}
			}
			fin = pos.test_fin();
			joueur = (joueur + 1) % 2;
		}
		System.out.println("Fin de la partie!");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Minimax algo = new Minimax();
		algo.start_game();
	}

}

/*
 * public final class Minimax { //KKKprivate int[][] cnext_; // [2][6];
 * //KKKprivate int[][] jnext_; // [2][6];
 * 
 * //YYY private ArrayDeque<Position> positionPool_; private Position[] pool_;
 * private int numPool_;
 * 
 * int [] val_; int [] coup_;
 * 
 * 
 * static final int VALMAX = 48; long NUM_MINIMAX = 0; int VALMM = 0;
 * 
 * 
 * Minimax() { //KKK cnext_ = new int[2][6]; //KKK jnext_ = new int[2][6];
 * pool_=new Position[64]; numPool_=64; //YYY positionPool_=new
 * ArrayDeque<Position>(64); for (int i=0;i<64;i++){ Position p=new Position();
 * pool_[i]=p; //YYY positionPool_.push(p); } val_=new int[6]; coup_=new int[6];
 * }
 * 
 * Position createPosition(){ return pool_[--numPool_]; ///YYY return
 * positionPool_.pop(); // return new Position(); } void
 * releasePosition(Position p){ // void releasePosition(){ //pool_[numPool_]=p;
 * numPool_++; //YYY positionPool_.push(p); }
 * 
 * 
 * int calculer_coup(Position pos, final int joueur, int alpha, int beta, final
 * int pmax, final boolean gagne) { Position newPos=createPosition(); if (joueur
 * == 0) {// MAX // cout << "MAX"; for (int i = 0; i < 6; i++) { //AAA if
 * (pos.cases_[joueur][i] > 0){ //AAA pos.copier(newPos); //AAA if
 * (newPos.jouer_coup(joueur, i)) { if (pos.jouer_coup(newPos, joueur, i)) {
 * final int val = valeur_minimaxAB(newPos, (joueur + 1) % 2, alpha, beta, pmax
 * - 1, gagne); if (val > alpha) { alpha = val; } if (alpha >= beta) { // cout
 * << "a"; break; } } //AAA } } // releasePosition(); releasePosition(newPos);
 * return alpha; } // cout << "MIN"; for (int i = 0; i < 6; i++) { //AAA if
 * (pos.cases_[joueur][i] > 0){ //AAA pos.copier(newPos); //AAA if
 * (newPos.jouer_coup(joueur, i)) { if (pos.jouer_coup(newPos, joueur, i)) {
 * final int val = valeur_minimaxAB(newPos, (joueur + 1) % 2, alpha, beta, pmax
 * - 1, gagne); // cout << " coup: " << i << " val: " << val << " alpha: " << //
 * alpha << " beta:" << beta << endl; if (val < beta) { beta = val; } if (beta
 * <= alpha) { // cout << "b"; break; } } //AAA } } // releasePosition();
 * releasePosition(newPos); return beta; }
 * 
 * int valeur_minimaxAB(Position pos, final int joueur, int alpha, int beta,
 * final int pmax, final boolean gagne) { // Calcule la valeur de e pour le
 * joueur J selon que e EstUnEtatMax ou // pas et pmax la NUM_MINIMAX++; int
 * ajoutProf = (gagne) ? pmax : 0; if (pos.pionsPris_[0] + pos.pionsPris_[1] >
 * 24) { if (48 - pos.pionsPris_[0] - pos.pionsPris_[1] <= 6) { if
 * (pos.pionsPris_[0] > pos.pionsPris_[1]) { // cout <<
 * "position finale gagnante joueur: " << joueur << // " pmax: " << pmax <<
 * endl; // print_position(pos); return VALMAX + ajoutProf; } if
 * (pos.pionsPris_[0] < pos.pionsPris_[1]) return -VALMAX - ajoutProf; return 0;
 * } if (pos.pionsPris_[0] >= 25) { // cout <<
 * "position finale gagnante joueur: " << joueur << " // pmax: " << pmax <<
 * endl; return VALMAX + ajoutProf; } if (pos.pionsPris_[1] >= 25) return
 * -VALMAX - ajoutProf; } if (pmax == 0) return pos.evaluer(); return
 * calculer_coup(pos, joueur, alpha, beta, pmax, gagne); }
 * 
 * int decisionAB(Position pos, int pmax, boolean gagne) { // on regarde le
 * nombre de case vide et on ajoute de la profondeur // eventuellement // k case
 * vide = profmax * k/12 int k = 0; for (int i = 0; i < 6; i++) { if
 * (pos.cases_[0][i] == 0) k++; // if (pos.cases_[1][i] == 0) k++; } // k--; if
 * (k > 0) { if (pos.pionsPris_[1] + pos.pionsPris_[0] >= 20) pmax++; if
 * (pos.pionsPris_[1] + pos.pionsPris_[0] >= 25) pmax++;
 * 
 * if (k <= 3) pmax++; if (k > 3) pmax += 2; } System.out.println("prof max: " +
 * pmax);
 * 
 * int alpha = -VALMAX - 50; // avant -1 int beta = VALMAX + 50; // avant +1
 * Position newPos=createPosition(); // pos.copier(newPos); int coup = 0; for
 * (int i = 0; i < 6; i++) { //AAA if (pos.cases_[0][i] > 0){ //AAA
 * pos.copier(newPos); //AAA if (newPos.jouer_coup(0, i)) { if
 * (pos.jouer_coup(newPos, 0, i)) { final int val = valeur_minimaxAB(newPos, 1,
 * alpha, beta, pmax - 1, gagne); if (val > alpha) { alpha = val; coup = i; } }
 * //AAA } }
 * 
 * VALMM = alpha; // releasePosition(); releasePosition(newPos); return coup; }
 * 
 * 
 * void start_game() {
 * 
 * Position pos = new Position(); Position newPos = new Position();
 * pos.position_debut();
 * 
 * System.out.println("DEBUT DU JEU AWALE"); System.out.println(
 * "(0) l'ordinateur commence"); System.out.println("(1) le joueur commence");
 * int joueur; Scanner sc = new Scanner(System.in); joueur = sc.nextInt();
 * 
 * boolean ordiCommence = (joueur == 0); System.out.println("C'est parti!");
 * 
 * boolean fin = false; boolean gagne = false;
 * 
 * StopWatch sw=new StopWatch();
 * 
 * while (!fin) { int coup; if (joueur == 0) { // l'ordi JOUE sw.start(); coup =
 * decisionAB(pos, 17, gagne); // ATTENTION C'est LA // PROFONDEUR MAX 11
 * sw.stop(); System.out.println("time: " + sw.elapsed_ms()); if (!gagne &&
 * VALMM == 48) { gagne = true; } System.out.println("Noeuds traites: " +
 * NUM_MINIMAX + " valeur minimax:" + VALMM); int cj; if (ordiCommence) { cj = 6
 * - coup; } else { cj = 12 - coup; } System.out.println("COUP JOUE: " + cj);
 * NUM_MINIMAX = 0; //AAA pos.jouer_coup(joueur, coup);
 * 
 * pos.copier(newPos); pos.jouer_coup(newPos, joueur, coup); newPos.copier(pos);
 * 
 * if (ordiCommence) { pos.print_position_ordi_haut_inv(); } else {
 * pos.print_position_ordi_bas_inv(); } } else { // le JOUEUR JOUE if
 * (ordiCommence) { System.out.println("selectionner une case 12 11 10 9 8 7");
 * coup = sc.nextInt(); coup -= 7; } else { System.out.println(
 * "selectionner une case 1 2 3 4 5 6"); coup = sc.nextInt(); // coup=6-coup;
 * coup--; } //AAA pos.jouer_coup(joueur, coup); pos.copier(newPos);
 * pos.jouer_coup(newPos, joueur, coup); newPos.copier(pos); if (ordiCommence) {
 * pos.print_position_ordi_haut_inv(); } else {
 * pos.print_position_ordi_bas_inv(); } } fin = pos.test_fin(); joueur = (joueur
 * + 1) % 2; } System.out.println("Fin de la partie!"); }
 * 
 * public static void main(String[] args) { // TODO Auto-generated method stub
 * Minimax algo=new Minimax(); algo.start_game(); }
 * 
 * }
 */