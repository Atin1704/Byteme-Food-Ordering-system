Assumptions-
Junit-
1.Instead of logging in a with an invalid user id and being denied access, the system in my third assignment
  used a logic that a new user id would be created where the user would have to create a password 
  and then re-enter it to confirm it. So that is what i am testing instead in one part of the login test.
2.In order contains unavailable items being denied i used a different logic in my previous assignment, the order
  would continue just without the unavailable, the user will have a option tho for confirming payment thru
  which he can stop the order, this creates two cases which i have covered in Junit-
  a)order with only unavailable items present , order is denied as cart would be empty after removal
  b),order having some unavailable items,order is allowed to proceed but the total updated cost will reflect the change after removal of the items, 
    this updated cost is used to test the correctness of the test, also because the test asks for a lot of string input
    a simulated place order function has been created that tests the necessary part of that function.


Sources used-
1.Java Code Geeks
2.Geeks for Geeks



