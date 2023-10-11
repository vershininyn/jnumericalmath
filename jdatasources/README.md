## jdatasourceslib

***A pure java implementation of data sources for a machine learning algorithms.***

## **The requirements**
<hr>

### **Functionality**

### **Usability**

### **Reliability**

### **Performance**

### **Supportability**

### **The limitation of design: Design**

### **The limitation of design: Implementation**

## Types of a data sources:
<hr>

## CSV:

### SupervisedDataSet:

- MultiDimensionalSupervisedDataSet: this is mapping a vector of independent features onto a vector of markers (
  dependent features) in the context of a supervised ML task.
- OneDimensionalSupervisedDataSet: this is mapping a vector of independent features onto a one marker (dependent
  feature) in the context of a supervised ML task.

### UsupervisedDataSet:

- MultiDimensionalUnsupervisedDataSet: it's a collection of independent variables vector's without mapping to marker
  vector (dependent feature's) in the context of a unsupervised ML task
- OneDimensionalUnsupervisedDataSet: it's a collection of independent variables vector of size = 1 without mapping to
  marker vector (dependent feature's) in the context of a unsupervised ML task
- Generators: it's generator of universal distribution (Gauss, for example) from a uniform distribution (use Lambda)

## UseCase [creation logic]:
<hr>

### Split to one dimension of a supervised data set
```Java
MultidimensionalSupervisedDataSet dataSet=new MultidimensionalSupervisedDataSet();

try(InputStream dataStream=...){
    dataSet.read(dataStream,1); // 1 = a marker dimension (1,2,3,...)
}

OneDimensionalSupervisedDataSet oneDataSet=dataSet.sliceToOneDimension(0); // zero is a slice index (0,1,2,...)
```

### Split to one dimension of a unsupervised data set
```Java
MultidimensionalUnsupervisedDataSet dataSet=new MultidimensionalUnsupervisedDataSet();

try(InputStream dataStream=...){
    dataSet.read(dataStream);
}

OneDimensionalUnsupervisedDataSet oneDataSet=dataSet.sliceToOneDimension(0); // zero is a slice index (0,1,2,...)
```

### Split by rows unsupervised data set
```Java
MultidimensionalUnsupervisedDataSet dataSet=new MultidimensionalUnsupervisedDataSet();

try(InputStream dataStream=...){
    dataSet.read(dataStream);
}

MultidimensionalUnsupervisedDataSet[]dataSetArray=dataSet.splitByRows(0.3);
//0.3 is coeff of first size sample,
//0 index is a first sample,1 is a second sample
```

## UseCase [performWindowTransformation logic]:
<hr>

Switching between samples for a learning task with and without a teacher (a marker or a dependent variable) is possible
as follows:

1. Generate a unsupervised sample

```Java
OneDimensionalUnsupervisedDataSet dataSet = new OneDimensionalUnsupervisedDataSet();

for (double index = -100.0; index < 100.0; index += 0.5) { 
  dataSet.add(Math.sin(index)); 
}
```

2. Switch to a supervised sample by means of performWindowTransformation method. This is the logic of a shifting window
   of size N. In this case, the marker is formed as each N+1 element of the original sample.

```Java
int windowSize = 10;
OneDimensionalSupervisedDataSet allDataSet = dataSet.performWindowTransformation(windowSize);
```

3. Use OneDimensionalSupervisedDataSet for parametric synthesis of a mathematical model.
   ...
