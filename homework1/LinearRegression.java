package homework1;

import java.util.Random;

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
		randomShuffle(trainingData);
		setAlpha(trainingData);
		m_coefficients = gradientDescent(trainingData);

	}

	private void randomShuffle(Instances trainingData) {
		Random random = new Random();
		trainingData.randomize(random);
	}

	private void setAlpha(Instances trainingData){
		int numberOfInstances = trainingData.numInstances();
		
		double powerOfError = -17;
		double minError = Double.MAX_VALUE;
		
		for(int i = -17; i < 2; i++) {
			double[] tetas = initialTetasArray();
			m_alpha = Math.pow(3, i);
			double error = 0;
			
			for(int j = 0; j < 20000; j++) {
				tetas = iterationInGradientDescent(trainingData, tetas);
			}
			
			for(int k = 0; k < numberOfInstances; k++) {
				Instance currentInstance = trainingData.instance(k);
				try {
					error += Math.pow(calculateError(currentInstance, tetas), 2);
				} catch (Exception e) {
					System.out.println("There is a problem to calculate the error");
				}
			}
			
			error = error / (double)numberOfInstances;
			
			if(error < minError) {
				minError = error;
				powerOfError = i;
			}
		}
		
		m_alpha = Math.pow(3, powerOfError);
		System.out.println("alpha is: 3^" + powerOfError);
	}
	
	private double[] initialTetasArray() {
		double[] tetas = new double[m_truNumAttributes + 1];
		
		for(int i = 0; i < tetas.length; i++) {
			tetas[i] = 1;
		}
		
		return tetas;
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
		
		boolean toContinue = true;
		double prevErrorResult = Double.MAX_VALUE; // ensure that will be more then one of 100 iterations of the algorithm
		double currentErrorResult = 0;
		
		while(toContinue) {
			for(int i = 0; i < 100; i++) {
				tetas = iterationInGradientDescent(trainingData, tetas);
			}
			
			currentErrorResult = calculateSE(trainingData, tetas);
			
			if(Math.abs(prevErrorResult - currentErrorResult) < 0.003) {
				toContinue = false;
			}else {
				prevErrorResult = currentErrorResult;
				currentErrorResult = 0;
			}
			
		}

		return tetas;
	}

	private double[] iterationInGradientDescent(Instances instances, double[] tetas) {
		int numberOfInstances = instances.numInstances();
		int numberOfTetas = tetas.length;
		double[] newTetas = new double[numberOfTetas];
		
		for(int j = 0; j < numberOfTetas; j++) {
			double sumError = 0.0;
			
			for(int i = 0; i < numberOfInstances; i++) {
				Instance currentInstance = instances.instance(i);
				double[] currentFeatures = copyFeaturesToArray(currentInstance);
				try {
					sumError += calculateError(currentInstance, tetas) * currentFeatures[j];
				} catch (Exception e1) {
					System.out.println("There is a problem to calculate the error");
				}
			}
			double e = (m_alpha * sumError / (double)numberOfInstances);
			newTetas[j] = tetas[j] - e;
		}
		
		return newTetas;
	}
	
	private double[] copyFeaturesToArray(Instance instance) {
		int length = instance.numValues();
		double[] featuresArray = new double[length + 1];
		featuresArray[0] = 1;
		int indexOffeature = 0;
		
		for(int i = 1; i < length + 1; i++) {
			featuresArray[i] = instance.value(indexOffeature); 
			indexOffeature++;
		}
		
		return featuresArray;
	}

	private double calculateError(Instance instance, double[] tetas) throws Exception {
		double classValue = instance.classValue();
		
		return regressionPrediction(instance, tetas) - classValue;
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
		double prediction = coefficients[0];
		for (int i = 0; i < m_truNumAttributes; i++) {
			if (instance.classIndex() != i) {
				prediction += instance.value(i) * coefficients[i + 1];
			}
			
		}
		
		return prediction;
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
		double totalError = 0;
		int numOfInstances = testData.numInstances();
		for (int i = 0; i < numOfInstances; i++) {
			totalError += Math.pow(calculateError(testData.instance(i), coefficients), 2);
		}
		
		return totalError / (double)numOfInstances;
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

	public double[] getCoefficients() {
		return m_coefficients;
		
	}
}
