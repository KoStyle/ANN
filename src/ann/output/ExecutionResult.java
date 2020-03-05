package ann.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ann.input.Case;

public class ExecutionResult {
	private ArrayList<TrainAndTestError> evolution= null;
	private double result;
	private ArrayList<NeuronValues> nv= new ArrayList<NeuronValues>();
	private ArrayList<NeuronValues> comboNv= new ArrayList<NeuronValues>();
	private ArrayList<Double> weights= new ArrayList<Double>();
	private ArrayList<Case> failures= new ArrayList<Case>();
	private ArrayList<NoiseResult> noiseResults= new ArrayList<NoiseResult>();
	private int iteration;
	
	public ExecutionResult(int iteration){
		this.iteration=iteration;
	}
	/**
	 * @return the result
	 */
	public double getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(double result) {
		this.result = result;
	}
	/**
	 * @return the nv
	 */
	public ArrayList<NeuronValues> getNv() {
		return nv;
	}
	public ArrayList<NeuronValues> getNvAndRes() {
		ArrayList<NeuronValues> ret= new ArrayList<NeuronValues>();
		ret.addAll(nv);
		NeuronValues res= new NeuronValues();
		res.setIteration(this.iteration);
		res.setResult(result);
		res.setDownValue(-2);
		ret.add(res);
		res.setNeuronID(-1);
		
		return ret;
		
	}
	
	public ArrayList<NeuronValues> getComboNvAndRes() {
		ArrayList<NeuronValues> ret= new ArrayList<NeuronValues>();
		ret.addAll(comboNv);
		NeuronValues res= new NeuronValues();
		res.setIteration(this.iteration);
		res.setResult(result);
		res.setDownValue(-2);
		res.setNeuronID(-1);
		ret.add(res);
		return ret;
		
	}
	/**
	 * @param nv the nv to set
	 */
	public void setNv(ArrayList<NeuronValues> nv) {
		this.nv = nv;
	}
	/**
	 * @return the evolution
	 */
	public ArrayList<TrainAndTestError> getEvolution() {
		return evolution;
	}
	/**
	 * @param evolution the evolution to set
	 */
	public void setEvolution(ArrayList<TrainAndTestError> evolution) {
		this.evolution = evolution;
	}
	public void addNeuronValues(NeuronValues nv2) {
		nv2.setIteration(iteration);
		this.nv.add(nv2);
		
	}
	
	public void addComboNeuronValues(NeuronValues nv2) {
		nv2.setIteration(iteration);
		this.comboNv.add(nv2);	
	}
	
	public void writeExecutionResult(File file) throws IOException{
		Workbook wb = new XSSFWorkbook();
		
		Sheet sheet = wb.createSheet("Hoja 1");
		int rowCount=0;
		
		Row row = sheet.createRow(rowCount);
		row.createCell(0).setCellValue("Result:");
		row.createCell(1).setCellValue(this.result);
		rowCount+=2;
		
		row=sheet.createRow(rowCount);
		row.createCell(0).setCellValue("Set of Weights");
		rowCount++;
		for(Double aux: this.weights){
			row=sheet.createRow(rowCount);
			row.createCell(0).setCellValue(aux);
			rowCount++;
		}
		rowCount+=2;
		
		row=sheet.createRow(rowCount);
		row.createCell(0).setCellValue("Neuron ID");
		row.createCell(1).setCellValue("Result");
		row.createCell(2).setCellValue("DownValue");
		row.createCell(3).setCellValue("UpValueMean");
		row.createCell(4).setCellValue("MaxRange");
		row.createCell(5).setCellValue("MinRange");
		rowCount++;
		
		for(NeuronValues aux: this.nv){
			row=sheet.createRow(rowCount);
			row.createCell(0).setCellValue(aux.getNeuronID());
			row.createCell(1).setCellValue(aux.getResult());
			row.createCell(2).setCellValue(aux.getDownValue());
			row.createCell(3).setCellValue(aux.getUpValue().getMean());
			row.createCell(4).setCellValue(aux.getUpValue().getMaxRange());
			row.createCell(5).setCellValue(aux.getUpValue().getMinRange());
			rowCount++;
			
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		wb.close();
		fileOut.close();
	}
	public void writeEvolution(File evolution2) throws IOException {
		Workbook wb = new XSSFWorkbook();
		
		Sheet sheet = wb.createSheet("Hoja 1");
		int rowCount=0;
		
		Row row = sheet.createRow(rowCount);
		row.createCell(0).setCellValue("Ciclo");
		row.createCell(1).setCellValue("Entrenamiento");
		row.createCell(2).setCellValue("Test");
		
		for(TrainAndTestError aux: this.evolution){
			rowCount++;
			row = sheet.createRow(rowCount);
			row.createCell(0).setCellValue(aux.getCicle());
			row.createCell(1).setCellValue(aux.getTraining());
			row.createCell(2).setCellValue(aux.getTest());
		}
		
		FileOutputStream fileOut = new FileOutputStream(evolution2);
	    wb.write(fileOut);
	    wb.close();
	    fileOut.close();
	}
	public ArrayList<NeuronValues> getComboNv() {
		return comboNv;
	}
	public void setComboNv(ArrayList<NeuronValues> comboNv) {
		this.comboNv = comboNv;
	}
	public ArrayList<Double> getWeights() {
		return weights;
	}
	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}
	/**
	 * @return the failures
	 */
	public ArrayList<Case> getFailures() {
		return failures;
	}
	/**
	 * @param failures the failures to set
	 */
	public void setFailures(ArrayList<Case> failures) {
		this.failures = failures;
	}
	public void writeFailures(File failures2) throws IOException {
		Workbook wb = new XSSFWorkbook();
		
		Sheet sheet = wb.createSheet("Hoja 1");
		int rowCount=0;
		if( this.failures.isEmpty()){
			wb.close();
			return;
		}
		Case x1= this.failures.get(0);
		
		Row row = sheet.createRow(rowCount);
		for(int i=0; i<x1.getnData(); i++){
			row.createCell(i).setCellValue("D"+(i+1));
		}
		row.createCell(x1.getnData()).setCellValue("Epsilon");
		row.createCell(x1.getnData()+1).setCellValue("Lambda");
		
		for(Case aux: this.failures){
			rowCount++;
			row = sheet.createRow(rowCount);
			for(int i=0; i<x1.getnData(); i++){
				row.createCell(i).setCellValue(aux.getData(i));
			}
			for(int i=x1.getnData(), j=0; i<x1.getnData()+x1.getnResults(); i++, j++){
				row.createCell(i).setCellValue(aux.getExpected().get(j));
			}
				
		}
		
		FileOutputStream fileOut = new FileOutputStream(failures2);
	    wb.write(fileOut);
	    wb.close();
	    fileOut.close();
	}
	public void addNeuronValuesExtra(NeuronValues nv2) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<NoiseResult> getNoiseResults() {
		return noiseResults;
	}
	public void setNoiseResults(ArrayList<NoiseResult> noiseResults) {
		this.noiseResults = noiseResults;
	}
	public void writeNoiseResults(File nr) throws IOException {
		Workbook wb = new XSSFWorkbook();
		 
		Sheet sheet = wb.createSheet("Hoja 1");
		int rowCount=0;
		if( this.noiseResults.isEmpty()){
			wb.close();
			return;
		}
		//comment
		
		Row row = sheet.createRow(rowCount);
		row.createCell(0).setCellValue("Noise Level");
		row.createCell(1).setCellValue("Result");
		for(NoiseResult aux: this.noiseResults){
			rowCount++;
			row = sheet.createRow(rowCount);
			row.createCell(0).setCellValue(aux.getNoiseLevel());
			row.createCell(1).setCellValue(aux.getResult());
		}
		
		FileOutputStream fileOut = new FileOutputStream(nr);
	    wb.write(fileOut);
	    wb.close();
	    fileOut.close();
	}
}
