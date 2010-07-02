package org.gcontracts.tests.pre

import org.gcontracts.tests.basic.BaseTestClass

/**
 * @author andre.steingress@gmail.com
 */
class InheritanceTests extends BaseTestClass {

  def source_parent = '''
package tests

import org.gcontracts.annotations.*

class Parent {

  @Requires({ param1 > 0 && param2 > 0  })
  def some_operation1(Integer param1, Integer param2)  {

  }

  boolean boolean_operation() {
    println "blue"
    return true
  }

  @Requires({ param1 > 0 && param2 > 1 })
  def some_operation3(Integer param1, Integer param2)  {

  }

  @Requires({ param1 > 0 && param2 > 0 })
  def some_operation4(Integer param1, Integer param2)  {
    println param1
    println param2
  }
}
'''

  def source_descendant = '''
package tests

import org.gcontracts.annotations.*

class Descendant extends Parent {

  @Override
  @Requires({ param1 > 1 && param2 > 1  })
  def some_operation1(Integer param1, Integer param2)  {

  }

  @Requires({ boolean_operation() })
  def some_operation2()  {

  }

  @Requires({ x > 0 && y > 0 })
  def some_operation3(Integer x, Integer y)  {

  }

}
'''

  def void test_redefined_precondition() throws Exception {
    create_instance_of(source_parent)
    def child = create_instance_of(source_descendant)

    child.some_operation1(1, 1)
  }

  def void test_redefined_precondition2() throws Exception {
    create_instance_of(source_parent)
    def child = create_instance_of(source_descendant)

    shouldFail AssertionError, {
      child.some_operation1(0, 0)
    }
  }

  def void test_method_call_of_super_class_in_precondition() throws Exception {
    create_instance_of(source_parent)
    def child = create_instance_of(source_descendant)

    println child.boolean_operation()

    child.some_operation2()
  }

  def void test_refined_precondition_with_other_param_names() throws Exception {
    create_instance_of(source_parent)
    def child = create_instance_of(source_descendant)

    shouldFail AssertionError, {
      child.some_operation3(0, 0)
    }
  }

  def void test_refined_precondition_with_other_param_names1() throws Exception {
    create_instance_of(source_parent)
    def child = create_instance_of(source_descendant)

    shouldFail AssertionError, {
      child.some_operation3(0, 1)
    }
  }

  def void test_refined_precondition_with_other_param_names2() throws Exception {
    create_instance_of(source_parent)
    def child = create_instance_of(source_descendant)

    child.some_operation3(1, 2)
  }


}