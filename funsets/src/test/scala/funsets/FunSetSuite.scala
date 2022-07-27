package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s12 = union(s1,s2)
    val s13 = union(s2,s3)
    val s23 = union(s2,s3)
    val s123 = union(s12,s3)
  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one".ignore) {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }
  
  test("[1,2] ^ [2,3] = [2]"){
    new TestSets:
      val s = intersect(s12,s23)
      assert(contains(s,2),"2 should be here")
      assert(!contains(s,1),"1 should not be here")
      assert(!contains(s,3),"3 should not be here")
  }

  test("[1,2,3] / [3] = [1,2]"){
    new TestSets:
      val s = diff(s123,s3)
      assert(contains(s,1),"1 should be here")
      assert(contains(s,2),"2 should be here")
      assert(!contains(s,3),"3 not should be here")
  }

  test("[1,2,3] R 2 = [2]"){ //return the subset that is [2] 
    new TestSets:
      val condition = (x:Int) => contains(s2,x) // não faz sentido
      val s = filter(s123,condition)
      assert(!contains(s,1),"1 not should be here")
      assert(contains(s,2),"2 should be here")
      assert(!contains(s,3),"3 not should be here")
  }


  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
