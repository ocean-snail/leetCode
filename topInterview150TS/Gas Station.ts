/*
There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].
You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station. 
You begin the journey with an empty tank at one of the gas stations.
Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, 
otherwise return -1. If there exists a solution, it is guaranteed to be unique.

Constraints:
n == gas.length == cost.length
1 <= n <= 105
0 <= gas[i], cost[i] <= 104
The input is generated such that the answer is unique.


Example 1:
Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.
---
-2,-2,-2,3,3
2.2.2-7.1
gas[i] - cost[i] <= 0 이면 시작불가
!
? 어떻게 계속 돌것인가?
[3] 0+4 > 1
[4] 3+5 > 2
[0] 6+1 > 3
[1] 4+2 > 4
[2] 2+3 > 5

! 시작 지점에 따라 시작값이 달라짐. 출발 지점이 달라도 순회하면서 계산되는 값은 변하지 않음.

*/

function canCompleteCircuit(gas: number[], cost: number[]): number {
  let totalGas = 0;
  let myCarTank = 0;
  let startPoint = 0;
  let leftGas: number;

  for (let i = 0; i < gas.length; i++) {
    leftGas = gas[i] - cost[i];
    totalGas += leftGas;
    myCarTank += leftGas;

    if (myCarTank < 0) {
      startPoint = i + 1;
      myCarTank = 0;
    }
  }

  return totalGas >= 0 ? startPoint : -1;
}

function logResult(gas: number[], cost: number[]) {
  const result = canCompleteCircuit(gas, cost);
  console.log(`result : ${result}`);
}

logResult([1, 2, 3, 4, 5], [3, 4, 5, 1, 2]); // 3
logResult([2, 3, 4], [3, 4, 3]); // -1
