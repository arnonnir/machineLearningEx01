package homework1;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class LinearRegression extends Classifier{
	
	private int m_ClassIndex;
	private int m_truNumAttributes;
	private double[] m_coefficients;
	private double m_alpha;
	
	//the method which runs to train the linear regression predictor, i.e.
	//finds its weights.
	@Override
	public void buildClassifier(Instances trainingData) throws Exception {
		trainingData = new Instances(trainingData);
		m_ClassIndex = trainingData.classIndex();
		//since class attribute is also an attribuite we subtract 1
		m_truNumAttributes = trainingData.numAttributes() - 1;
		setAlpha();
		m_coefficients = gradientDescent(trainingData);
		
	}
	
	private void setAlpha(){
		
	}
	
	/**
	 * An implementation of the gradient descent algorithm which should try
	 * to return the weights of a linear regression predictor which minimizes
	 * the average squared error.
	 * @param trainingData
	 * @return
	 * @throws Exception
	 */
	public double[] gradientDescent(Instances trainingData) throws Exception {
		double[] tetas = new double[m_truNumAttributes + 1];
		for(int i = 0; i < tetas.length; i++) {
			tetas[i] = 1;
		}
		
		
		return null;
	}
	
	/**
	 * Returns the prediction of a linear regression predictor with weights
	 * given by coefficients on a single instance.
	 * @param instance
	 * @param coefficients
	 * @return
	 * @throws Exception
	 */
	public double regressionPrediction(Instance instance, double[] coefficients) throws Exception {
		return 0;
	}
	
	/**
	 * Calculates the total squared error over the test data on a linear regression
	 * predictor with weights given by coefficients.
	 * @param testData
	 * @param coefficients
	 * @return
	 * @throws Exception
	 */
	public double calculateSE(Instances testData, double[] coefficients) throws Exception {

		return 0;
	}
	
	/**
	 * Finds the closed form solution to linear regression with one variable.
	 * Should return the coefficient that is to be multiplied
	 * by the input attribute.
	 * @param data
	 * @return
	 */
	public double findClosedForm1D(Instances data){
		return 0;
	}

}
