package recfun

import scala.collection.immutable.Stream.Cons

object RecFun extends RecFunInterface:

  def main(args: Array[String]): Unit =
    println("Pascal's Triangle")
    for row <- 0 to 10 do
      for col <- 0 to row do
        print(s"${pascal(col, row)} ")
      println()

  /**
   * Exercise 1
   * Computes the value a specific number from the pascal triangle
   * @param c column number (starting at 0)
   * @param r row number (starting at 0)
   * @return (Int) the value computed from a specific tuple (c,r)
   */
  def pascal(c: Int, r: Int): Int = {
    if((c == 0) || (c == r)) 
      1 //the edges of the triangle are always 1
    else
      pascal(c-1,r-1) + pascal(c,r-1) //the classic way to calculate an element is to sum the above numbers (possible using tail ?)
  }
    
  /**
   * Exercise 2
   * Tells you if a list of chars has the parenthesis balenced
   * For all ( existes a ) to close it
   * @param chars the list of chars to be evaluated
   * @return (Boolean)
   */
  
  def balance(chars: List[Char]): Boolean = {
    def balance_status(chars: List[Char],status: Int): Int ={
      if(chars.isEmpty)
        status
      else if(chars.head == '(')
        balance_status(chars.tail,status+1)
      else if(chars.head == ')' && status > 0) // if status is less than 0 the expression is no longer balanced
        balance_status(chars.tail,status-1)
      else
        balance_status(chars.tail,status) // ignores other chars
    }
    if (chars.count(_.equals('(')) == chars.count(_.equals(')')))
      balance_status(chars,0) == 0
    else
      false
    
  }

  /**
   * Exercise 3
   * Calculates how many ways you can make change with a set of coins
   * @param money value to achieve
   * @param coins set of coins
   * @return (Int) how many ways you can make change
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def countChangeWays(money: Int, coins: List[Int], sum:Int): Int={
      if(coins.isEmpty) sum
      else if(money == 0) 
        1+sum // it exists only one way to give 0
        else if(coins.length == 1 && money % coins.head == 0) 
          1+sum //assuming the list is ordered
        else if(coins.head>money) 
          countChangeWays(money,coins.tail,sum)
        else
          countChangeWays(money,coins.tail,sum+countChangeWays(money-coins.head,coins,0))
    }
    countChangeWays(money,coins,0)
  }
