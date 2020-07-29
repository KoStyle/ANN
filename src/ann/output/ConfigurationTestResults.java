package ann.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ann.input.Case;

public class ConfigurationTestResults {

	private File rootFolder;
	private ArrayList<ExecutionResult> bpResults= new ArrayList<ExecutionResult>();
	private ArrayList<ExecutionResult> agResults= new ArrayList<ExecutionResult>();
	private ArrayList<ExecutionResult> appResults= new ArrayList<ExecutionResult>();
	private ArrayList<ArrayList<NeuronValues>> execution= new ArrayList<ArrayList<NeuronValues>>();
	private ArrayList<ArrayList<NeuronValues>> comboExecution= new ArrayList<ArrayList<NeuronValues>>();
	public  ArrayList<Case> dataset = null;
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
	
	public ConfigurationTestResults(File rootFolder) {
		super();
		this.rootFolder = rootFolder;
	}
	public void addComboIteration(ArrayList<NeuronValues> it){
		this.comboExecution.add(it);
	}
	public void addIteration(ArrayList<NeuronValues> it){
		this.execution.add(it);
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
	/**
	 * @return the agResults
	 */
	public ArrayList<ExecutionResult> getAgResults() {
		return agResults;
	}
	/**
	 * @return the appResults
	 */
	public ArrayList<ExecutionResult> getAppResults() {
		return appResults;
	}
	/**
	 * @return the bpResults
	 */
	public ArrayList<ExecutionResult> getBpResults() {
		return bpResults;
	}
	/**
	 * @return the cICLES
	 */
	public double getCICLES() {
		return CICLES;
	}
	/**
	 * @return the cROSSOVER
	 */
	public double getCROSSOVER() {
		return CROSSOVER;
	}
	/**
	 * @return the gENERATIONS
	 */
	public int getGENERATIONS() {
		return GENERATIONS;
	}
	/**
	 * @return the gENERR
	 */
	public double getGENERR() {
		return GENERR;
	}
	/**
	 * @return the hIGHPEN
	 */
	public double getHIGHPEN() {
		return HIGHPEN;
	}
	/**
	 * @return the iNDVIDUAL_PAIRS
	 */
	public int getINDVIDUAL_PAIRS() {
		return INDVIDUAL_PAIRS;
	}
	/**
	 * @return the iNIRANG
	 */
	public double getINIRANG() {
		return INIRANG;
	}
	public ArrayList<ArrayList<NeuronValues>> getIteration() {
		return execution;
	}
	/**
	 * @return the lEARNING_FACTOR
	 */
	public double getLEARNING_FACTOR() {
		return LEARNING_FACTOR;
	}
	/**
	 * @return the lOWPEN
	 */
	public double getLOWPEN() {
		return LOWPEN;
	}
	/**
	 * @return the lOWRANG
	 */
	public double getLOWRANG() {
		return LOWRANG;
	}
	/**
	 * @return the mAX_ERROR
	 */
	public double getMAX_ERROR() {
		return MAX_ERROR;
	}
	/**
	 * @return the mEDPEN
	 */
	public double getMEDPEN() {
		return MEDPEN;
	}
	/**
	 * @return the mIDRANG
	 */
	public double getMIDRANG() {
		return MIDRANG;
	}
	/**
	 * @return the mOMENTUM_FACTOR
	 */
	public double getMOMENTUM_FACTOR() {
		return MOMENTUM_FACTOR;
	}
	/**
	 * @return the mUTCTE
	 */
	public double getMUTCTE() {
		return MUTCTE;
	}
	/**
	 * @return the mUTFACT
	 */
	public double getMUTFACT() {
		return MUTFACT;
	}
	/**
	 * @return the nEGATIVE_PREDICTION
	 */
	public double getNEGATIVE_PREDICTION() {
		return NEGATIVE_PREDICTION;
	}
	/**
	 * @return the nHidden
	 */
	public int getnHidden() {
		return nHidden;
	}
	/**
	 * @return the nNeuHidden
	 */
	public int getnNeuHidden() {
		return nNeuHidden;
	}
	/**
	 * @return the out
	 */
	public int getOut() {
		return out;
	}
	/**
	 * @return the params
	 */
	public int getParams() {
		return params;
	}
	/**
	 * @return the pOSITIVE_PREDICTION
	 */
	public double getPOSITIVE_PREDICTION() {
		return POSITIVE_PREDICTION;
	}
	/**
	 * @return the rootFolder
	 */
	public File getRootFolder() {
		return rootFolder;
	}
	/**
	 * @return the mOMENTUM_FLAG
	 */
	public boolean isMOMENTUM_FLAG() {
		return MOMENTUM_FLAG;
	}
	/**
	 * @return the oUTTYPE
	 */
	public boolean isOUTTYPE() {
		return OUTTYPE;
	}
	/**
	 * @return the pENALTY
	 */
	public boolean isPENALTY() {
		return PENALTY;
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
	
	/**
	 * @param agResults the agResults to set
	 */
	public void setAgResults(ArrayList<ExecutionResult> agResults) {
		this.agResults = agResults;
	}
	
	/**
	 * @param appResults the appResults to set
	 */
	public void setAppResults(ArrayList<ExecutionResult> appResults) {
		this.appResults = appResults;
	}
	
	/**
	 * @param bpResults the bpResults to set
	 */
	public void setBpResults(ArrayList<ExecutionResult> bpResults) {
		this.bpResults = bpResults;
	}
	
	/**
	 * @param cICLES the cICLES to set
	 */
	public void setCICLES(double cICLES) {
		CICLES = cICLES;
	}
	
	/**
	 * @param cROSSOVER the cROSSOVER to set
	 */
	public void setCROSSOVER(double cROSSOVER) {
		CROSSOVER = cROSSOVER;
	}
	
	/**
	 * @param gENERATIONS the gENERATIONS to set
	 */
	public void setGENERATIONS(int gENERATIONS) {
		GENERATIONS = gENERATIONS;
	}
	
	/**
	 * @param gENERR the gENERR to set
	 */
	public void setGENERR(double gENERR) {
		GENERR = gENERR;
	}
	
	/**
	 * @param hIGHPEN the hIGHPEN to set
	 */
	public void setHIGHPEN(double hIGHPEN) {
		HIGHPEN = hIGHPEN;
	}
	
	/**
	 * @param iNDVIDUAL_PAIRS the iNDVIDUAL_PAIRS to set
	 */
	public void setINDVIDUAL_PAIRS(int iNDVIDUAL_PAIRS) {
		INDVIDUAL_PAIRS = iNDVIDUAL_PAIRS;
	}
	/**
	 * @param iNIRANG the iNIRANG to set
	 */
	public void setINIRANG(double iNIRANG) {
		INIRANG = iNIRANG;
	}
	public void setIteration(ArrayList<ArrayList<NeuronValues>> iteration) {
		this.execution = iteration;
	}
	
	/**
	 * @param lEARNING_FACTOR the lEARNING_FACTOR to set
	 */
	public void setLEARNING_FACTOR(double lEARNING_FACTOR) {
		LEARNING_FACTOR = lEARNING_FACTOR;
	}
	/**
	 * @param lOWPEN the lOWPEN to set
	 */
	public void setLOWPEN(double lOWPEN) {
		LOWPEN = lOWPEN;
	}
	/**
	 * @param lOWRANG the lOWRANG to set
	 */
	public void setLOWRANG(double lOWRANG) {
		LOWRANG = lOWRANG;
	}
	
	/**
	 * @param mAX_ERROR the mAX_ERROR to set
	 */
	public void setMAX_ERROR(double mAX_ERROR) {
		MAX_ERROR = mAX_ERROR;
	}
	
	/**
	 * @param mEDPEN the mEDPEN to set
	 */
	public void setMEDPEN(double mEDPEN) {
		MEDPEN = mEDPEN;
	}
	/**
	 * @param mIDRANG the mIDRANG to set
	 */
	public void setMIDRANG(double mIDRANG) {
		MIDRANG = mIDRANG;
	}
	/**
	 * @param mOMENTUM_FACTOR the mOMENTUM_FACTOR to set
	 */
	public void setMOMENTUM_FACTOR(double mOMENTUM_FACTOR) {
		MOMENTUM_FACTOR = mOMENTUM_FACTOR;
	}
	/**
	 * @param mOMENTUM_FLAG the mOMENTUM_FLAG to set
	 */
	public void setMOMENTUM_FLAG(boolean mOMENTUM_FLAG) {
		MOMENTUM_FLAG = mOMENTUM_FLAG;
	}
	/**
	 * @param mUTCTE the mUTCTE to set
	 */
	public void setMUTCTE(double mUTCTE) {
		MUTCTE = mUTCTE;
	}
	/**
	 * @param mUTFACT the mUTFACT to set
	 */
	public void setMUTFACT(double mUTFACT) {
		MUTFACT = mUTFACT;
	}
	/**
	 * @param nEGATIVE_PREDICTION the nEGATIVE_PREDICTION to set
	 */
	public void setNEGATIVE_PREDICTION(double nEGATIVE_PREDICTION) {
		NEGATIVE_PREDICTION = nEGATIVE_PREDICTION;
	}
	/**
	 * @param nHidden the nHidden to set
	 */
	public void setnHidden(int nHidden) {
		this.nHidden = nHidden;
	}
	/**
	 * @param nNeuHidden the nNeuHidden to set
	 */
	public void setnNeuHidden(int nNeuHidden) {
		this.nNeuHidden = nNeuHidden;
	}
	/**
	 * @param out the out to set
	 */
	public void setOut(int out) {
		this.out = out;
	}
	/**
	 * @param oUTTYPE the oUTTYPE to set
	 */
	public void setOUTTYPE(boolean oUTTYPE) {
		OUTTYPE = oUTTYPE;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(int params) {
		this.params = params;
	}
	/**
	 * @param pENALTY the pENALTY to set
	 */
	public void setPENALTY(boolean pENALTY) {
		PENALTY = pENALTY;
	}
	/**
	 * @param pOSITIVE_PREDICTION the pOSITIVE_PREDICTION to set
	 */
	public void setPOSITIVE_PREDICTION(double pOSITIVE_PREDICTION) {
		POSITIVE_PREDICTION = pOSITIVE_PREDICTION;
	}
	/**
	 * @param rootFolder the rootFolder to set
	 */
	public void setRootFolder(File rootFolder) {
		this.rootFolder = rootFolder;
	}
	public void writeNeurons(File file, ArrayList<NeuronValues> nv) throws IOException{
		PrintWriter bf=new PrintWriter(new FileWriter(file));
		
		bf.println("Deactivated Neuron ID; Result; DownValue; UpValueMean; MaxRange; MinRange");
		for(NeuronValues aux: nv){
			bf.println(aux.getNeuronID()+";"+aux.getResult()+";"+aux.getDownValue()+";"+aux.getUpValue().getMean()+";"+aux.getUpValue().getMaxRange()+";"+aux.getUpValue().getMinRange());
		}
		
		bf.close();
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
	
	/** @return the greatest common denominator */
	private static long gcd(long a, long b) {
	    return b == 0 ? a : gcd(b, a % b);
	}

	private static String asFraction(long a, long b) {
	    long gcd = gcd(a, b);
	    return (a / gcd) + ":" + (b / gcd);
	}
	
	public void writeResultsDB(Connection conn, int id_setup, int id_result) throws SQLException {
		if(conn == null) {
			throw new SQLException("Null database connection in writeResultsDB");
		}
		
		String insert_statement = "INSERT INTO NNRESULS VALUES (?, ?, ?, ?)";
		String insert_evo_statement = "INSERT INTO NNRESULTEVO VALUES (?, ?, ?, ?, ?)";
		
		//TODO Add support for multiple result writes.Right now it only supports one result to be written (because of parallel runs and the id_result value)
		//We get the first (if more than one) result
		ExecutionResult er = null;
		if(this.bpResults.size()>0) {
			er= this.bpResults.get(0);
		}else if (this.agResults.size()>0) {
			er=this.agResults.get(0);
		}else if (this.appResults.size()>0) {
			er=this.appResults.get(0);
		}
		
		if(er != null) {
			int caseZero=0;
			int caseOne=0;
			
			for(Case cs:this.dataset) {
				if(cs.getExpected().get(0)==0) {  //if the first expected (first output neuron for a 2 class classifier) is 0, increase counter
					caseZero++;
				}
			}
			caseOne= this.dataset.size()-caseZero;
			
			String ratio = "Ratio cases 0 to cases 1= " + ConfigurationTestResults.asFraction(caseZero, caseOne);
			try {
				PreparedStatement query= conn.prepareStatement(insert_statement);
				query.setInt(1, id_setup);
				query.setInt(2, id_result);
				query.setDouble(3, er.getResult());
				query.setString(4, ratio);
				query.execute();
				query.close();
				for(TrainAndTestError tat: er.getEvolution()) {
					query= conn.prepareStatement(insert_evo_statement);
					query.setInt(1, id_setup);
					query.setInt(2, id_result);
					query.setInt(3, tat.getCicle());
					query.setDouble(4, tat.getTraining());
					query.setDouble(5, tat.getTest());
					query.execute();
					query.close();
				}
				if(!conn.getAutoCommit()) {					
					conn.commit();
				}
			}catch (SQLException e){
				if(!conn.getAutoCommit()) {					
					conn.rollback();
				}
				e.printStackTrace();
				throw new SQLException("Couldn't write the result in the DB");
			}
			
			
			
			
			
			
		}
		
		
		
	}
}
