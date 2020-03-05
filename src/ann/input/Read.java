package ann.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import ann.util.NeuronConfig;

public class Read {

	public static ArrayList<Double> max = null;
	public static ArrayList<Double> min = null;
	public static final String IronMan = "C:\\Users\\Konomi\\Dropbox\\workspace";
	public static final String SilverCenturion = "C:\\Users\\Manu\\Dropbox\\workspace";
	public static final String rutaBase=null;
	public static final String CANCER = "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\CANCER\\Cancer.net";
	public static final String CANCER2 = "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\CANCER2\\Cancer.net";
	public static final String PIMA = "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\PIMA\\Pima.net";
	public static final String CARD = "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\CARD\\Card.net";
	public static final String HEART = "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\HEART\\Heart.net";
	public static final String SONAR = "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\SONAR\\Sonar.net";
	public static final String GLASS = "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\GLASS\\Glass.net";
	public static final String HORSE = "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\HORSE\\Horse.net";
	public static final String CURVAS = "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\CURVAS\\Curvas3.txt";
	public static final String CURVAS2 = "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\CURVAS\\Curvas2.txt";

	public Read() {
	};
	
	@Deprecated
	public static ArrayList<Case> read(String file) {

		int cont;
		String line, n = null;
		String[] lines = new String[2];
		ArrayList<Case> cases = new ArrayList<Case>();

		try {

			BufferedReader filein = new BufferedReader(new FileReader(file));

			while ((line = filein.readLine()) != null) {

				Case aux = new Case(3,6);
				StringTokenizer st = new StringTokenizer(line);

				cont = 1;
				while (st.hasMoreTokens()) {
					n = st.nextToken();
					switch (cont) {
					case 3:
						lines = n.split("e");
						aux.addData(Double.parseDouble(lines[0])
								* Math.pow(10, Double.parseDouble(lines[1])));
						break;
					case 19:
						lines = n.split("e");
						aux.addData(Double.parseDouble(lines[0])
								* Math.pow(10, Double.parseDouble(lines[1])));
						break;
					case 20:
						lines = n.split("e");
						aux.addData(Double.parseDouble(lines[0])
								* Math.pow(10, Double.parseDouble(lines[1])));
						break;
					case 22:
						lines = n.split("e");
//						aux.setExpected(Double.parseDouble(lines[0])
//								* Math.pow(10, Double.parseDouble(lines[1])));
						break;
					}
					cont++;
				}
				cases.add(aux);
			}
			filein.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return cases;
	}

	public static ArrayList<Case> read2(String file, int nParam, int nResult) {

		int cont;
		String line, n = null;
		ArrayList<Case> cases = new ArrayList<Case>();

		try {

			BufferedReader filein = new BufferedReader(new FileReader(file));

			while ((line = filein.readLine()) != null) {

				Case aux = new Case(nParam, nResult);
				StringTokenizer st = new StringTokenizer(line);

				cont = 0;
				while (st.hasMoreTokens() && cont < nParam) {
					n = st.nextToken();
					aux.addData(Double.parseDouble(n));
					cont++;
				}

				if (!st.hasMoreTokens()) {
					filein.close();
					throw new Exception("BAD CASE FILE 2");
				}

				cont = 0;
				while (st.hasMoreTokens() && cont < nResult) {
					n = st.nextToken();
					aux.addExpected(Double.parseDouble(n));
					cont++;
				}
				if (!st.hasMoreTokens() && cont < nResult) {
					filein.close();
					throw new Exception("BAD CASE FILE 2 RESULT NO MATCH");
				}

				cases.add(aux);
			}
			filein.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return cases;
	}

	/**
	 * @param file
	 * @return
	 */
	public static ArrayList<Case> readAndNormalise(String file) {
		ArrayList<Case> cases = Read.read(file);
		Case tmp = cases.get(0);
		int nData = tmp.getnData();

		if (max == null || min == null) {
			max = new ArrayList<Double>();
			min = new ArrayList<Double>();

			for (int i = 0; i <= nData; i++) {
				max.add(0.);
				min.add(Double.MAX_VALUE);
			}

			/* Obtencion del maximo y el minimo para cada atributo */
			for (Case aux : cases) {
				for (int i = 0; i < nData; i++) {
					if (aux.getData(i) > max.get(i)) {
						max.set(i, aux.getData(i));
					} else if (aux.getData(i) < min.get(i)) {
						min.set(i, aux.getData(i));
					}
				}
				/*
				 * if(aux.getExpected()>max.get(nData)){ max.set(nData,
				 * aux.getExpected()); }else
				 * if(aux.getExpected()<min.get(nData)){ min.set(nData,
				 * aux.getExpected()); }
				 */
			}
		}

		/* Normalizacion de los atributos */

		for (Case aux : cases) {
			for (int i = 0; i < nData; i++) {
				double normalised = 0;
				normalised = aux.getData(i) - min.get(i);
				normalised = normalised / (max.get(i) - min.get(i));
				aux.setData(normalised, i);
			}

			/*
			 * double normalised = 0; normalised = aux.getExpected() -
			 * min.get(nData); normalised = normalised / (max.get(nData) -
			 * min.get(nData)); aux.setExpected(normalised);
			 */

		}

		return cases;
	}

	// lay, npl, dlf, param
	// id, lf, fail, failtype, activation, sigmoids.

	/*
	 * TOKENS:: ID (Id of a neuron) LF (learning factor of a neuron) FAIL
	 * (percentage of failure in the output) FTYPE (phases where the failure
	 * will be applied) ACT (phases where the neuron will or not be active) ALFA
	 * B G
	 */
	public static ArrayList<NeuronConfig> readConfig(String file,
			double lEARNING_FACTOR, int nNeurons) {
		double dlf = lEARNING_FACTOR;
		String line;

		ArrayList<NeuronConfig> configs = new ArrayList<NeuronConfig>();
		for (int i = 0; i < nNeurons; i++) {
			NeuronConfig c = new NeuronConfig(dlf, i);
			configs.add(c);
		}
		if (file != null) {
			try {

				BufferedReader filein = new BufferedReader(new FileReader(file));

				int i = 0;
				while ((line = filein.readLine()) != null) {
					NeuronConfig c = new NeuronConfig(0, 0);

					// ID
					if (i < 10) {
						int index = line.indexOf("ID");
						if (index == -1) {
							filein.close();
							throw new Exception(
									"Error de formato en fichero de configuracion: ID");
						}

						String aux = line.substring(2 + index + 1,
								2 + index + 2);
						c.setId(Integer.parseInt(aux));
					} else {
						int index = line.indexOf("ID");
						if (index == -1) {
							filein.close();
							throw new Exception(
									"Error de formato en fichero de configuracion: ID");
						}

						String aux = line.substring(2 + index + 1,
								2 + index + 3);
						c.setId(Integer.parseInt(aux));
					}

					// LEARNING FACTOR
					int index = line.indexOf("LF");
					if (index != -1) {
						String aux = line.substring(2 + index + 1,
								2 + index + 5);
						c.setLf(Double.parseDouble(aux));
					}

					// FAIL
					index = line.indexOf("FAIL");
					if (index != -1) {
						String aux = line.substring(4 + index + 1,
								4 + index + 5);
						c.setFailure(Double.parseDouble(aux));
					}

					// FAIL TYPE

					index = line.indexOf("FTYPE");
					if (index != -1) {
						String aux = line.substring(4 + index + 1,
								4 + index + 2);
						c.setFailureType(Integer.parseInt(aux));
					}

					// ACTIVATION STATE
					index = line.indexOf("ACT");
					if (index != -1) {
						String aux = line.substring(3 + index + 1,
								3 + index + 2);
						int tmp3 = Integer.parseInt(aux);
						if (tmp3 == 1) {
							c.setActivation(true);
						} else {
							c.setActivation(false);
						}

					}

					// ALFA
					index = line.indexOf("ALFA");
					if (index != -1) {
						String aux = line.substring(4 + index + 1,
								4 + index + 5);
						c.setAlfa(Double.parseDouble(aux));
					}

					// BETA
					index = line.indexOf("B");
					if (index != -1) {
						String aux = line.substring(1 + index + 1,
								1 + index + 5);
						c.setBeta(Double.parseDouble(aux));
					}

					// GAMMA
					index = line.indexOf("G");
					if (index != -1) {
						String aux = line.substring(1 + index + 1,
								1 + index + 5);
						c.setGamma(Double.parseDouble(aux));
					}

					configs.set(c.getId(), c);
					i++;
				}

				filein.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return configs;
	}

	public static void main(String[] args) {
		ArrayList<NeuronConfig> list = new ArrayList<NeuronConfig>();
		ArrayList<Case> cases = new ArrayList<Case>();
	//	list = Read.readConfig(IronMan + "\\RED.txt");
		cases = Read.read2(IronMan+ "\\Set de Casos\\Set de Casos\\Set de Casos MARN 1.3\\CANCER\\Cancer.net", 9, 1);
		list.size();
	}
}
