Changelog [v0.21.2]
1. Stricter criteria for selecting the best algorithm in the multithreaded testing scheme. 
Convolution is used in terms of time (milliseconds), the number of iterations, 
the calculation of the function, the subgradient.
2. The SortingAscendingTest is completed

Changelog [v0.21.1]
1. The minimax issue is resolved
2. Some refactor of the code

Changelog [v0.21]
1. The sigmoid and hyperbolic tangent functionally conditioned multistep direction search algorithm is completed

Changelog [v0.20.4]
1. A FirstOrderLearningLinearSearchOptimalParametersDTO is completed

Changelog [v0.20.3]
1. A stop criteria quality is completed

Changelog [v0.20.2]
1. Some update of testing logic at AbstractFunctionalTests.
2. Some update of FirstOrderLearningOptimizationAttributes. Improved readability.
3. Some MultidimensionalUnsupervisedDataSet docs.
4. README file is updated.

Changelog [v0.20.1]
1. Split by rows logic of a multidimensional unsupervised data set is completed
2. Logic of a normal distribution at Kohonen network is corrected 

Changelog [v0.20]
1. A Kohonen self organizing map is completed

Changelog [v0.19.5]
1. Multidiminsional and onedimensional supervised/unsupervised data set's logic is simplified

Changelog [v0.19.4]
1. Fix of alpha and beta space tension algorithm is completed. Now it's more stable(without NaN).

Changelog [v0.19.3]
1. Behaviour of first order method's is more stable
2. Little fixes

Changelog [v0.19.2]
1. A sqrt logic of alpha-beta matrix correction operator is fixed
2. A sqrt logic of FirstOrderNYVSpaceTensionDirectionSearchAlgorithm is fixed
3. A sqrt logic of another matrix correction operator's is fixed

Changelog [v0.19.1]
1. A HTML reporting for group testing is completed 

Changelog [v0.19]
1. A composite (global + local) optimization algorithm is completed

Changelog [v0.18.2]
1. A bor-bug at getOptimumResult of zero order global optimization master is fixed
2. A reset logic of AbstractScalarDifferentiableFunction is changed
3. A alpha tension coefficient at AbstractFunctionalTests of a alpha STO is changed to 1.5
4. A alpha tension coefficient at FOOA group tests of a alpha STO is changed to 1.5

Changelog [v0.18.1]
1. A equals and hash codes of algorithms is updated
2. A stabilizing coeff of a orthogonal direction search algorithm is completed

Changelog [v0.18]
1. A first order orthogonal Learning optimization algorithm is completed
2. A orthogonal matrix correction operator is completed

Changelog [v0.17.3]
1. A bor-bug at a global optimization algorithm is fixed

Changelog [v0.17.2]
1. A stop criteria by a multiplicity factor of global zero order optimization algorithm is completed
2. A little fixes of unconstrained optimization algorithm is completed

Changelog [v0.17.1]
1. A concurrent version of global zero order unconstrained optimization is completed
2. A test of above is completed
3. int of a results and attributes --> long

Changelog [v0.17]
1. A global informational statistics unconstrained zero order optimization algorithm is completed
2. A test of above is completed

Changelog [v0.16.2]
1. Correction of a cubic step interpolation algorithm is completed
2. A little fixes

Changelog [v0.16.1]
1. A right model correction at a Kachmazh, multistep and pairwise space tension DSA is completed
2. A Kachmazh internal direction correction operator is completed

Changelog [v0.16]
1. A CriticalGradientNormValueIsAchievedException (a another stop condition) is completed
2. A right model correction at a Kachmazh, multistep and pairwise DSA is completed

Changelog [v0.15.3]
1. A meta optimization test of a coefficients of a FOKLSA and other hyperparameters is in progress

Changelog [v0.15.2]
1. A checkStopCondition bor-bug is fixed

Changelog [v0.15.1]
1. ZeroOrderLearningOrthogonalUnconstrainedOptimizationAlgorithmTests is corrected

Changelog [v0.15]
1. A zero order Learning orthogonal unconstrained optimization algorithm is completed
2. Reorginized of common logic is completed
3. A little fixes of packages structure is completed

Changelog [v0.14]
1. A alpha space tension operator heuristic is completed

Changelog [v0.13]
1. Computing of a winners algorithms at function group logic is completed
2. A hash code and equals logic is completed

Changelog [v0.12]
1. A master-slave concurrent testing logic is reorganized

Changelog [v0.11.2]
1. Added a not smooth functions's group to concurrent group test

Changelog [v0.11.1]
1. Logging logic of concurrent testing at smooth functions's group is completed

Chagelog [v0.11]
1. A sum of coordinate smooth function is completed
2. A Eckley smooth function is completed
3. A generalized Rosenbrock smooth function is completed
4. A Rastrigin smooth function is completed
5. A Schwefel smooth function is completed
6. A Grivenka smooth function is completed
7. Each test of above smooth function's is completed
8. A concurrent group test of above smooth functions's is completed

Changelog [v0.10]
1. A group testing logic is completed

Changelog [v0.9]
1. A adopted increase step of linear search algorithm is completed
2. A cubic interpolation heuristic is completed

Chagelog [v0.8]
1. A fixes of NYV and NGH algorithms logic is completed
2. A new stop criteria (Euclid norm of gradient) is realized

Chagelog [v0.7]
1. A Kachmazh, multistep and pairwise space tension direction search algorithms is completed
2. A alpha-beta space tension operator is completed [issue]

Changelog [v0.6]
1. A space tension matrix correction operator is completed
2. A NGN space tension direction search algorithm is completed

Changelog [v0.5.9]:
1. A space tension and shrinkage matrix correction operator is completed
2. A NYV space tension direction search algorithm is completed

Changelog [v0.5.8]:
1. A sum barrier function is completed
2. A tests of sum barrier function with a hyper plane model is completed

Changelog [v0.5.7]:
1. A penalty function -> a max barrier function
2. A max and abs constraint wrappers is completed
3. A little fixes of logic

Changelog [v0.5.6]:
1. A penalty logic is updated: a less then zero and
   more then zero constraints is completed
2. A little fixes

Changelog [v0.5.5]:
1. A penalty max function is completed
2. A least squares and max functional's is refactored
3. A jdatasources is completed
4. A little fixes is completed

Changelog [v0.5]:
1. A max function repeated compute count
   and shutdown optimization process time stop criteria is completed
2. A Tihonov regularization decorator is completed
3. Code is refactored

Changelog [v0.4]:
1. A LeastSquareFunctionalWithRegularization is released
2. A HyperPlaneModel is completed
3. A data.csv is released
4. A MaxFunctional is released

Changelog [v0.3]:
1. A IDataSource, CsvFileDataSource is released
2. A LoggableException is updated

Changelog [v0.2]:
1. A splited DenseMatrix into a matrix and vector entities
2. A code refactored
4. A stop criteria is released

Changelog [v0.1]:
1. A standart sets of control parameters
2. ILearningDirectionSearchAlgorithm interface is introduced
3. qMin and qMax parameters check is released