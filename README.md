# jnumericalmath

The integration project of the other libs

## **Motivation**

The key idea of integrating self-sufficient libraries into the system is
determined by the relevance and prevalence of mathematical programming applications

The development process and its ultimate goal strive for a balance
between three concepts: the creative joy of implementing a software system, my java developer hard skills
and the benefits for the community. The idea of a balance between these concepts is taken from Ikigai.

## **The requirements** of libraries system

See the descriptions of the requirements for the corresponding library

## **The architecture** of libraries system

![img.png](doc/img.png)

The architecture of the library system is a very common multi-level structure pattern.

## **The responsibility** of each library:

<table>
  <tr>
    <th>The library</th>
    <th>The responsibility</th>
    <th>The dependencies</th>
  </tr>
  <tr>
    <td>jexception</td>
    <td>The library used end-to-end in other libraries 
exceptions with additional information for debugging</td>
    <td>None</td>
  </tr>
  <tr>
    <td>jlinalglib</td>
    <td>The linear algebra algorithms library</td>
    <td>jexception</td>
  </tr>
  <tr>
    <td>jdatasourceslib</td>
    <td>The data sources for machine learning algorithms library</td>
    <td>jexception</td>
  </tr>
  <tr>
    <td>jtransformationslib</td>
    <td>The mathematical analysis mapping entities library</td>
    <td>
        <ul>
            <li>jexception</li>
            <li>jdatasourceslib</li>
            <li>jlinalglib</li>
        </ul>
    </td>
  </tr>
  <tr>
    <td>jnumericaloptlib</td>
    <td>The numerical optimization algorithms library</td>
    <td>
        <ul>
            <li>jexception</li>
            <li>jlinalglib</li>
            <li>jtransformationslib</li>
            <li>jdatasourceslib</li>
        </ul>
    </td>
  </tr>
  <tr>
    <td>jmachinelearninglib</td>
    <td>The machine learning algorithms library</td>
    <td>
        <ul>
            <li>jexception</li>
            <li>jlinalglib</li>
            <li>jnumericaloptlib</li>
            <li>jdatasourceslib</li>
            <li>jtransformationslib</li>
        </ul>
    </td>
  </tr>
</table>