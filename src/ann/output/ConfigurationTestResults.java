package ann.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConfigurationTestResults {

	private File rootFolder;
	private ArrayList<ExecutionResult> bpResults= new ArrayList<ExecutionResult>();
	private ArrayList<ExecutionResult> agResults= new ArrayList<ExecutionResult>();
	private ArrayList<ExecutionResult> appResults= new ArrayList<ExecutionResult>();
	private ArrayList<ArrayList<NeuronValues>> execution= new ArrayList<ArrayList<NeuronValues>>();
	private ArrayList<ArrayList<NeuronValues>> comboExecution= new ArrayList<ArrayList<NeuronValues>>();
	private double meanResultBp, meanResultAg, meanResultApp, maxBp, maxAg, minBp, minAg, maxApp, minApp;
	private int params, nHidden, nNeuHidden, out;
	
	private double POSITIVE_PREDICTION;
	private double NEGATIVE_PREDICTION;
	
	private double MAX_ERROR;
	private double CICLES;
	private double LEARNING_FACTOR;
	private double MOMENTUM_FACTOR;
	private boolean MOMENTUM_FLAG;
	
	private boolean OUTTYPE;
	
	private boolean PENALTY;
	private double INIRANG ;
	private double MIDRANG;
	private double LOWRANG;
	private double HIGHPEN;
	private double MEDPEN;
	private double LOWPEN;
	
	private int INDVIDUAL_PAIRS;
	private int GENERATIONS;
	private double CROSSOVER;
	private double MUTCTE ;
	private double MUTFACT ;
	private double GENERR ;
	/**
	 * @return the rootFolder
	 */
	public File getRootFolder() {
		return rootFolder;
	}
	/**
	 * @param rootFolder the rootFolder to set
	 */
	public void setRootFolder(File rootFolder) {
		this.rootFolder = rootFolder;
	}
	/**
	 * @return the bpResults
	 */
	public ArrayList<ExecutionResult> getBpResults() {
		return bpResults;
	}
	/**
	 * @param bpResults the bpResults to set
	 */
	public void setBpResults(ArrayList<ExecutionResult> bpResults) {
		this.bpResults = bpResults;
	}
	/**
	 * @return the agResults
	 */
	public ArrayList<ExecutionResult> getAgResults() {
		return agResults;
	}
	/**
	 * @param agResults the agResults to set
	 */
	public void setAgResults(ArrayList<ExecutionResult> agResults) {
		this.agResults = agResults;
	}
	/**
	 * @return the appResults
	 */
	public ArrayList<ExecutionResult> getAppResults() {
		return appResults;
	}
	/**
	 * @param appResults the appResults to set
	 */
	public void setAppResults(ArrayList<ExecutionResult> appResults) {
		this.appResults = appResults;
	}
	/**
	 * @return the mAX_ERROR
	 */
	public double getMAX_ERROR() {
		return MAX_ERROR;
	}
	/**
	 * @param mAX_ERROR the mAX_ERROR to set
	 */
	public void setMAX_ERROR(double mAX_ERROR) {
		MAX_ERROR = mAX_ERROR;
	}
	/**
	 * @return the cROSSOVER
	 */
	public double getCROSSOVER() {
		return CROSSOVER;
	}
	/**
	 * @param cROSSOVER the cROSSOVER to set
	 */
	public void setCROSSOVER(double cROSSOVER) {
		CROSSOVER = cROSSOVER;
	}
	/**
	 * @return the mUTCTE
	 */
	public double getMUTCTE() {
		return MUTCTE;
	}
	/**
	 * @param mUTCTE the mUTCTE to set
	 */
	public void setMUTCTE(double mUTCTE) {
		MUTCTE = mUTCTE;
	}
	/**
	 * @return the oUTTYPE
	 */
	public boolean isOUTTYPE() {
		return OUTTYPE;
	}
	/**
	 * @param oUTTYPE the oUTTYPE to set
	 */
	public void setOUTTYPE(boolean oUTTYPE) {
		OUTTYPE = oUTTYPE;
	}
	/**
	 * @return the mUTFACT
	 */
	public double getMUTFACT() {
		return MUTFACT;
	}
	/**
	 * @param mUTFACT the mUTFACT to set
	 */
	public void setMUTFACT(double mUTFACT) {
		MUTFACT = mUTFACT;
	}
	/**
	 * @return the gENERR
	 */
	public double getGENERR() {
		return GENERR;
	}
	/**
	 * @param gENERR the gENERR to set
	 */
	public void setGENERR(double gENERR) {
		GENERR = gENERR;
	}
	/**
	 * @return the pENALTY
	 */
	public boolean isPENALTY() {
		return PENALTY;
	}
	/**
	 * @param pENALTY the pENALTY to set
	 */
	public void setPENALTY(boolean pENALTY) {
		PENALTY = pENALTY;
	}
	/**
	 * @return the iNIRANG
	 */
	public double getINIRANG() {
		return INIRANG;
	}
	/**
	 * @param iNIRANG the iNIRANG to set
	 */
	public void setINIRANG(double iNIRANG) {
		INIRANG = iNIRANG;
	}
	/**
	 * @return the mIDRANG
	 */
	public double getMIDRANG() {
		return MIDRANG;
	}
	/**
	 * @param mIDRANG the mIDRANG to set
	 */
	public void setMIDRANG(double mIDRANG) {
		MIDRANG = mIDRANG;
	}
	/**
	 * @return the lOWRANG
	 */
	public double getLOWRANG() {
		return LOWRANG;
	}
	/**
	 * @param lOWRANG the lOWRANG to set
	 */
	public void setLOWRANG(double lOWRANG) {
		LOWRANG = lOWRANG;
	}
	/**
	 * @return the hIGHPEN
	 */
	public double getHIGHPEN() {
		return HIGHPEN;
	}
	/**
	 * @param hIGHPEN the hIGHPEN to set
	 */
	public void setHIGHPEN(double hIGHPEN) {
		HIGHPEN = hIGHPEN;
	}
	/**
	 * @return the mEDPEN
	 */
	public double getMEDPEN() {
		return MEDPEN;
	}
	/**
	 * @param mEDPEN the mEDPEN to set
	 */
	public void setMEDPEN(double mEDPEN) {
		MEDPEN = mEDPEN;
	}
	/**
	 * @return the lOWPEN
	 */
	public double getLOWPEN() {
		return LOWPEN;
	}
	/**
	 * @param lOWPEN the lOWPEN to set
	 */
	public void setLOWPEN(double lOWPEN) {
		LOWPEN = lOWPEN;
	}
	/**
	 * @return the iNDVIDUAL_PAIRS
	 */
	public int getINDVIDUAL_PAIRS() {
		return INDVIDUAL_PAIRS;
	}
	/**
	 * @param iNDVIDUAL_PAIRS the iNDVIDUAL_PAIRS to set
	 */
	public void setINDVIDUAL_PAIRS(int iNDVIDUAL_PAIRS) {
		INDVIDUAL_PAIRS = iNDVIDUAL_PAIRS;
	}
	/**
	 * @return the gENERATIONS
	 */
	public int getGENERATIONS() {
		return GENERATIONS;
	}
	/**
	 * @param gENERATIONS the gENERATIONS to set
	 */
	public void setGENERATIONS(int gENERATIONS) {
		GENERATIONS = gENERATIONS;
	}
	
	public void addResultBP(ExecutionResult er){
		this.bpResults.add(er);
		double tmp=0;
		this.addIteration(er.getNvAndRes());
		this.addComboIteration(er.getComboNvAndRes());
		for(ExecutionResult aux: this.bpResults){
			tmp+=aux.getResult();
		}
		this.meanResultBp=tmp/this.bpResults.size();
	}
	
	public void addResultAG(ExecutionResult er){
		this.agResults.add(er);
		double tmp=0;
		this.addIteration(er.getNvAndRes());
		this.addComboIteration(er.getComboNvAndRes());
		for(ExecutionResult aux: this.agResults){
			tmp+=aux.getResult();
		}
		this.meanResultAg=tmp/this.agResults.size();
	}
	
	public void addResultAPP(ExecutionResult er){
		this.appResults.add(er);
		double tmp=0;
		this.addIteration(er.getNvAndRes());
		this.addComboIteration(er.getComboNvAndRes());
		for(ExecutionResult aux: this.appResults){
			tmp+=aux.getResult();
		}
		this.meanResultApp=tmp/this.appResults.size();
	}
	
	public ConfigurationTestResults(File rootFolder) {
		super();
		this.rootFolder = rootFolder;
	}
	
	public void writeResults() throws IOException{
		File directory=this.rootFolder;
		this.maxAg=0.;
		for(ExecutionResult aux: this.agResults){
			if(aux.getResult()>this.maxAg){
				this.maxAg=aux.getResult();
			}
		}
		
		this.minAg=1.;
		for(ExecutionResult aux: this.agResults){
			if(aux.getResult()<this.minAg){
				this.minAg=aux.getResult();
			}
		}
		
		this.maxBp=0.;
		for(ExecutionResult aux: this.bpResults){
			if(aux.getResult()>this.maxBp){
				this.maxBp=aux.getResult();
			}
		}
		
		this.minBp=1.;
		for(ExecutionResult aux: this.bpResults){
			if(aux.getResult()<this.minBp){
				this.minBp=aux.getResult();
			}
		}
		
		this.maxApp=0.;
		for(ExecutionResult aux: this.appResults){
			if(aux.getResult()>this.maxApp){
				this.maxApp=aux.getResult();
			}
		}
		
		this.minApp=1.;
		for(ExecutionResult aux: this.appResults){
			if(aux.getResult()<this.minApp){
				this.minApp=aux.getResult();
			}
		}

		if(!directory.exists()){
			directory.mkdirs();
		}
		
		File conf= new File(this.rootFolder + "\\summary.txt");
		conf.createNewFile();
		FileWriter fw=new FileWriter(conf);
		PrintWriter pw= new PrintWriter(fw);
		
		pw.println("CONFIGURACION DEL SET DE EJECUCIONES");
		pw.println();
		pw.println("MAX ERROR BP: "+ this.MAX_ERROR);
		pw.println("LEARNING FACTOR BP: "+ this.LEARNING_FACTOR);
		pw.println("MOMENTUM FACTOR: "+ this.MOMENTUM_FLAG);
		pw.println("MOMENTUM FACTOR: "+ this.MOMENTUM_FACTOR);
		pw.println();
		pw.println("CROSSOVER PROBABILITY: "+ this.CROSSOVER);
		pw.println("MUTATION PROBABILITY: "+ this.MUTFACT);
		pw.println("MUTATION CONSTANT: "+ this.MUTCTE);
		pw.println("MAX ERROR AG: "+ this.GENERR);
		pw.println("GENERATIONS AG: "+ this.GENERATIONS);
		pw.println("PAIRS OF INDIVIDUALS: "+ this.INDVIDUAL_PAIRS);
		pw.println();
		pw.println("OUT TYPE: "+ this.OUTTYPE);
		pw.println("POSITIVE LIMIT: " + this.POSITIVE_PREDICTION);
		pw.println("NEGATIVE LIMIT: " + this.NEGATIVE_PREDICTION);
		pw.println();
		pw.println("PENALTY INITIAL RANGE: "+ this.INIRANG);
		pw.println("PENALTY MEDIUM RANGE: "+ this.MIDRANG);
		pw.println("PENALTY LOW RANGE: "+ this.LOWRANG);
		pw.println();
		pw.println("HIGH PENALTY: "+ this.HIGHPEN);
		pw.println("MEDIUM PENALTY: "+ this.MEDPEN);
		pw.println("LOW PENALTY: "+ this.LOWPEN);
		pw.println();
		pw.println("AVERAGE BP: "+ this.meanResultBp);
		pw.println("MAX BP: " + this.maxBp);
		pw.println("MIN BP: " + this.minBp);
		pw.println();
		pw.println("AVERAGE AG: "+ this.meanResultAg);
		pw.println("MAX AG: " + this.maxAg);
		pw.println("MIN AG: " + this.minAg);
		pw.println();
		pw.println("AVERAGE APP: "+ this.meanResultApp);
		pw.println("MAX APP: " + this.maxApp);
		pw.println("MIN APP: " + this.minApp);
		pw.println();
		pw.println("ARCHITECTURE");
		pw.println("INPUTS: "+ this.params);
		pw.println("HIDDEN LAYERS: "+ this.nHidden);
		pw.println("NEURONS PER LAYER: "+ this.nNeuHidden);
		pw.println("OUTPUTS: "+ this.out);
		
		fw.close();
		ArrayList<NeuronValues> inputnvs= new ArrayList<NeuronValues>();
		ArrayList<NeuronValues> nvs= new ArrayList<NeuronValues>();
		
		
		int i =1;
		//TODO This should be recycled in a function
		for(ExecutionResult aux: this.bpResults){
			File f= new File(this.rootFolder, "BP"+i);
			f.mkdir();
			File results= new File(f.getAbsolutePath(), "BP"+i+".xlsx");
			aux.writeExecutionResult(results);
			
			if(aux.getEvolution()!=null){
				File evolution= new File(f.getAbsolutePath(), "BP"+i+"_evolution.xlsx");
				aux.writeEvolution(evolution);
			}
			if(aux.getFailures()!=null){
				File failures= new File(f.getAbsolutePath(), "BP"+i+"_failure_cases.xlsx");
				aux.writeFailures(failures);
			}
			if(aux.getNoiseResults()!=null){
				File nr= new File(f.getAbsolutePath(), "BP"+i+"_noise_results.xlsx");
				aux.writeNoiseResults(nr);
			}
			i++;
			for(int j=0; j<9; j++){
				inputnvs.add(aux.getNv().get(j));
			}
			for(int j=9; j<aux.getNv().size(); j++){
				nvs.add(aux.getNv().get(j));
			}
		}
		
		i =1;
		for(ExecutionResult aux: this.agResults){
			File f= new File(this.rootFolder, "AG"+i);
			f.mkdir();
			
			File results= new File(f, "AG"+i+".xlsx");
			results.createNewFile();
			
			aux.writeExecutionResult(results);
			if(aux.getEvolution()!=null){
				File evolution= new File(f, "AG"+i+"_evolution.xlsx");
				evolution.createNewFile();
				aux.writeEvolution(evolution);
			}
			if(aux.getFailures()!=null){
				File failures= new File(f.getAbsolutePath(), "AG"+i+"_failure_cases.xlsx");
				aux.writeFailures(failures);
			}
			if(aux.getNoiseResults()!=null){
				File nr= new File(f.getAbsolutePath(), "AG"+i+"_noise_results.xlsx");
				aux.writeNoiseResults(nr);
			}
			i++;
			for(int j=0; j<9; j++){
				inputnvs.add(aux.getNv().get(j));
			}
			for(int j=9; j<aux.getNv().size(); j++){
				nvs.add(aux.getNv().get(j));
			}
		}
		
		i =1;
		for(ExecutionResult aux: this.appResults){
			File f= new File(this.rootFolder, "APP"+i);
			f.mkdir();
			
			File results= new File(f, "APP"+i+".xlsx");
			results.createNewFile();
			
			aux.writeExecutionResult(results);
			if(aux.getEvolution()!=null){
				File evolution= new File(f, "APP"+i+"_evolution.xlsx");
				evolution.createNewFile();
				aux.writeEvolution(evolution);
			}
			if(aux.getFailures()!=null){
				File failures= new File(f.getAbsolutePath(), "APP"+i+"_failure_cases.xlsx");
				aux.writeFailures(failures);
			}
			if(aux.getNoiseResults()!=null){
				File nr= new File(f.getAbsolutePath(), "APP"+i+"_noise_results.xlsx");
				aux.writeNoiseResults(nr);
			}
			i++;
			for(int j=0; j<9; j++){
				inputnvs.add(aux.getNv().get(j));
			}
			for(int j=9; j<aux.getNv().size(); j++){
				nvs.add(aux.getNv().get(j));
			}
		}
		
		
		//SORTING NEURONS BY ID
		Collections.sort(inputnvs, new Comparator<NeuronValues>() {

			@Override
			public int compare(NeuronValues o1, NeuronValues o2) {
				return o1.getNeuronID()-o2.getNeuronID();
			}
		});
		File f= new File(this.rootFolder, "InputNeuronsByID.txt");
		f.createNewFile();
		this.writeNeurons(f, inputnvs);
		
		Collections.sort(nvs, new Comparator<NeuronValues>() {

			@Override
			public int compare(NeuronValues o1, NeuronValues o2) {
				return o1.getNeuronID()-o2.getNeuronID();
			}
		});
		
		f= new File(this.rootFolder, "NeuronsByID.txt");
		f.createNewFile();
		this.writeNeurons(f, nvs);
		
		//SORTING NEURONS BY SUCCESS
		Collections.sort(inputnvs, new Comparator<NeuronValues>() {

			@Override
			public int compare(NeuronValues o1, NeuronValues o2) {
				double buffer= o1.getResult()-o2.getResult();
				if(buffer>0){
					return -1;
				}else if (buffer==0){
					return 0;
				}else{
					return 1;
				}
			}
		});
		f= new File(this.rootFolder, "InputNeuronsBySuccess.txt");
		f.createNewFile();
		this.writeNeurons(f, inputnvs);
		
		Collections.sort(nvs, new Comparator<NeuronValues>() {

			@Override
			public int compare(NeuronValues o1, NeuronValues o2) {
				double buffer= o1.getResult()-o2.getResult();
				if(buffer>0){
					return -1;
				}else if (buffer==0){
					return 0;
				}else{
					return 1;
				}
			}
		});
		
		f= new File(this.rootFolder, "NeuronsBySuccess.txt");
		f.createNewFile();
		this.writeNeurons(f, nvs);
		
		//Writing neurons By execution
		this.neuronsByIteration();
		this.neuronsByComboIteration();
		
		
		
	}
	
	private void neuronsByIteration() throws IOException{
		Collections.sort(this.execution, new Comparator<ArrayList<NeuronValues>>() {

			@Override
			public int compare(ArrayList<NeuronValues> o1,
					ArrayList<NeuronValues> o2) {
				return o1.get(0).getIteration() - o2.get(0).getIteration();
			}
		});
		
		for(ArrayList<NeuronValues> aux: this.execution){
			Collections.sort(aux, new Comparator<NeuronValues>() {

				@Override
				public int compare(NeuronValues o1, NeuronValues o2) {
					return o1.getNeuronID()-o2.getNeuronID();
				}
			});
		}
		
		File f= new File(this.rootFolder, "NeuronsByIteration.xlsx");
		f.createNewFile();
		this.writeNeuronsIterations(f, this.execution);	
	}
	
	private void neuronsByComboIteration() throws IOException{
		Collections.sort(this.execution, new Comparator<ArrayList<NeuronValues>>() {

			@Override
			public int compare(ArrayList<NeuronValues> o1,
					ArrayList<NeuronValues> o2) {
				return o1.get(0).getIteration() - o2.get(0).getIteration();
			}
		});
		
		for(ArrayList<NeuronValues> aux: this.execution){
			Collections.sort(aux, new Comparator<NeuronValues>() {

				@Override
				public int compare(NeuronValues o1, NeuronValues o2) {
					int tmp=o1.getNeuronID()-o2.getNeuronID();
					if(tmp==0){
						return o1.getNeuronID2()-o2.getNeuronID2();
					}else{
						return tmp;
					}
				}
			});
		}
		
		File f= new File(this.rootFolder, "NeuronsByComboIteration.xlsx");
		f.createNewFile();
		this.writeNeuronsComboIterations(f, this.comboExecution);	
	}
	
	private void writeNeuronsIterations(File f, ArrayList<ArrayList<NeuronValues>> iteration2) throws IOException {
		Workbook wb = new XSSFWorkbook();
		
		Sheet sheet = wb.createSheet("Hoja 1");
		int rowCount=0;
		Row row = sheet.createRow(rowCount);
		row.createCell(0).setCellValue("Execution");
		row.createCell(1).setCellValue("General");
		int column=2;
		ArrayList<NeuronValues> nv= iteration2.get(0);
		for(int i=1; i<nv.size(); i++){
			row.createCell(column).setCellValue("Neuron "+ nv.get(i).getNeuronID());
			column++;
		}
		rowCount++;
		for(ArrayList<NeuronValues> tmp1 : iteration2){
			row=sheet.createRow(rowCount);
			row.createCell(0).setCellValue(tmp1.get(0).getIteration());
			column=1;
			for(NeuronValues tmp2 : tmp1){
				row.createCell(column).setCellValue(tmp2.getResult());
				column++;
			}
			rowCount++;
			
		}
		FileOutputStream fileOut = new FileOutputStream(f);
		wb.write(fileOut);
		wb.close();
		fileOut.close();
		
	}
	
	private void writeNeuronsComboIterations(File f, ArrayList<ArrayList<NeuronValues>> iteration2) throws IOException {
		
		Workbook wb = new XSSFWorkbook();
		
		Sheet sheet = wb.createSheet("Hoja 1");
		int rowCount=0;
		Row row = sheet.createRow(rowCount);
		row.createCell(0).setCellValue("Execution");
		row.createCell(1).setCellValue("General");
		int column=2;
		
		ArrayList<NeuronValues> nv= iteration2.get(0);
		for(int i=1; i<nv.size(); i++){
			row.createCell(column).setCellValue("Neuron "+ nv.get(i).getNeuronID()+ "-"+ nv.get(i).getNeuronID2());
			column++;
		}
		rowCount++;
		for(ArrayList<NeuronValues> tmp1 : iteration2){
			row=sheet.createRow(rowCount);
			row.createCell(0).setCellValue(tmp1.get(0).getIteration());
			column=1;
			for(NeuronValues tmp2 : tmp1){
				row.createCell(column).setCellValue(tmp2.getResult());
				column++;
			}
			rowCount++;
			
		}
		FileOutputStream fileOut = new FileOutputStream(f);
		wb.write(fileOut);
		wb.close();
		fileOut.close();
	}
	/**
	 * @return the lEARNING_FACTOR
	 */
	public double getLEARNING_FACTOR() {
		return LEARNING_FACTOR;
	}
	/**
	 * @param lEARNING_FACTOR the lEARNING_FACTOR to set
	 */
	public void setLEARNING_FACTOR(double lEARNING_FACTOR) {
		LEARNING_FACTOR = lEARNING_FACTOR;
	}
	
	public void writeNeurons(File file, ArrayList<NeuronValues> nv) throws IOException{
		PrintWriter bf=new PrintWriter(new FileWriter(file));
		
		bf.println("Deactivated Neuron ID; Result; DownValue; UpValueMean; MaxRange; MinRange");
		for(NeuronValues aux: nv){
			bf.println(aux.getNeuronID()+";"+aux.getResult()+";"+aux.getDownValue()+";"+aux.getUpValue().getMean()+";"+aux.getUpValue().getMaxRange()+";"+aux.getUpValue().getMinRange());
		}
		
		bf.close();
	}
	public ArrayList<ArrayList<NeuronValues>> getIteration() {
		return execution;
	}
	public void setIteration(ArrayList<ArrayList<NeuronValues>> iteration) {
		this.execution = iteration;
	}
	
	public void addIteration(ArrayList<NeuronValues> it){
		this.execution.add(it);
	}
	
	public void addComboIteration(ArrayList<NeuronValues> it){
		this.comboExecution.add(it);
	}
	/**
	 * @return the pOSITIVE_PREDICTION
	 */
	public double getPOSITIVE_PREDICTION() {
		return POSITIVE_PREDICTION;
	}
	/**
	 * @param pOSITIVE_PREDICTION the pOSITIVE_PREDICTION to set
	 */
	public void setPOSITIVE_PREDICTION(double pOSITIVE_PREDICTION) {
		POSITIVE_PREDICTION = pOSITIVE_PREDICTION;
	}
	/**
	 * @return the nEGATIVE_PREDICTION
	 */
	public double getNEGATIVE_PREDICTION() {
		return NEGATIVE_PREDICTION;
	}
	/**
	 * @param nEGATIVE_PREDICTION the nEGATIVE_PREDICTION to set
	 */
	public void setNEGATIVE_PREDICTION(double nEGATIVE_PREDICTION) {
		NEGATIVE_PREDICTION = nEGATIVE_PREDICTION;
	}
	/**
	 * @return the cICLES
	 */
	public double getCICLES() {
		return CICLES;
	}
	/**
	 * @param cICLES the cICLES to set
	 */
	public void setCICLES(double cICLES) {
		CICLES = cICLES;
	}
	/**
	 * @return the mOMENTUM_FACTOR
	 */
	public double getMOMENTUM_FACTOR() {
		return MOMENTUM_FACTOR;
	}
	/**
	 * @param mOMENTUM_FACTOR the mOMENTUM_FACTOR to set
	 */
	public void setMOMENTUM_FACTOR(double mOMENTUM_FACTOR) {
		MOMENTUM_FACTOR = mOMENTUM_FACTOR;
	}
	/**
	 * @return the mOMENTUM_FLAG
	 */
	public boolean isMOMENTUM_FLAG() {
		return MOMENTUM_FLAG;
	}
	/**
	 * @param mOMENTUM_FLAG the mOMENTUM_FLAG to set
	 */
	public void setMOMENTUM_FLAG(boolean mOMENTUM_FLAG) {
		MOMENTUM_FLAG = mOMENTUM_FLAG;
	}
	/**
	 * @return the params
	 */
	public int getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(int params) {
		this.params = params;
	}
	/**
	 * @return the nHidden
	 */
	public int getnHidden() {
		return nHidden;
	}
	/**
	 * @param nHidden the nHidden to set
	 */
	public void setnHidden(int nHidden) {
		this.nHidden = nHidden;
	}
	/**
	 * @return the nNeuHidden
	 */
	public int getnNeuHidden() {
		return nNeuHidden;
	}
	/**
	 * @param nNeuHidden the nNeuHidden to set
	 */
	public void setnNeuHidden(int nNeuHidden) {
		this.nNeuHidden = nNeuHidden;
	}
	/**
	 * @return the out
	 */
	public int getOut() {
		return out;
	}
	/**
	 * @param out the out to set
	 */
	public void setOut(int out) {
		this.out = out;
	}
}
