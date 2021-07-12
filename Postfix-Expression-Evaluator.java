import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

interface IExpressionEvaluator {
  
/**
* Takes a symbolic/numeric infix expression as input and converts it to
* postfix notation. There is no assumption on spaces between terms or the
* length of the term (e.g., two digits symbolic or numeric term)
*
* @param expression infix expression
* @return postfix expression
*/
  
public String infixToPostfix(String expression);
  
  
/**
* Evaluate a postfix numeric expression, with a single space separator
* @param expression postfix expression
* @return the expression evaluated value
*/
  
public int evaluate(String expression);

}

class Node{
    Object data;
    Node next;
}


class MyStack {
      
     static Node top;
     static int size=0; 
    
     public void push(Object element){
        Node node = new Node();
        node.data = element;
        node.next = top;
        top = node;
        size++;
     }
    
    
     public Object pop(){
        if(top == null)
            throw new NullPointerException("Error");
        Node temp = top;
        top = top.next;
        size--;
        return temp.data;
     }
    
    
     public Object peek(){
        if(top == null)
            throw new NullPointerException("Error");
        return top.data;
     }
    
    
     public int size(){
        return size;
     }
     
    
     public boolean isEmpty(){
        return(size() == 0); 
     }
    
     public void show(){
        if(top == null){
            System.out.println("[]");
        }else{
        
            Node p = top;
            System.out.print("[");
            while(p.next!= null){
                System.out.print(p.data + ", ");
                p = p.next;
            }
            System.out.println(p.data + "]");
        }
    }
}  


public class Evaluator implements IExpressionEvaluator{
    
    static MyStack a = new MyStack();
    String A, b, c;
         String i1,i2,i3;
     public int preceed(char i) {

        if (i == '(' || i == ')'){
            return 1;
        }else if (i == '-' || i == '+'){
            return 2;
        }else if (i == '*' || i == '/'){
            return 3;
        }else if(i == '^'){
            return 4;
        }else{
            return 0;
        }    
    }
    

    public  String infixToPostfix(String expression) {
        
        String postfix = "";
        char p;

        for (int i = 0; i < expression.length(); i++) {
            char op = expression.charAt(i);
            if (!(preceed(op) > 0)){
                postfix += op;
            }else if (op == ')'){
                
                while ((p = (char)a.pop()) != '('){
                    postfix += p;
                }
            }else{
                while (a.size()>=1 && op != '('){
                    if(preceed((char)a.peek()) >= preceed(op)){
                       postfix += a.pop();
                    }else{
                        break;
                    }
                }    
                a.push(op);
            }
        }
        while (!a.isEmpty() && (char)a.peek()!='('){
            postfix += a.pop();
        }
        if(!a.isEmpty()  ){
            throw new NullPointerException("Error");
        }
        return postfix;
    }
    
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////  
    public int evaluate(String expression){
         i1 = i1.replaceAll("a","");
         A=i1;
         A = A.replaceAll("=-","#"); 
         A= A.replaceAll("=","&");
        
         i2 = i2.replaceAll("b","");
         b=i2;
         b= b.replaceAll("=-","#"); 
         b= b.replaceAll("=","&"); 
    
         i3 = i3.replaceAll("c","");
         c=i3;
         c= c.replaceAll("=-","#"); 
         c= c.replaceAll("=","&"); 
         String eval = expression;
         eval = (expression).replaceAll("a",A);
         eval = eval.replaceAll("b",b);
         eval = eval.replaceAll("c",c);
         expression = eval;
         
 
    int x = 0, y = 0;
    char ch[] = expression.toCharArray();
     
    for(int i=0;i<ch.length;i++) {
      

         
       if(a.size()==1 && i==ch.length-1) {
            y = -1 * (int)a.pop() ;
          
            return y; 
        }
          
           if(a.size()==1 &&ch[i]!='#' && ch[i]!='&') {
           
            int w = -1 * (int)a.pop() ;
             a.push(w);
        
        } 
          
          
      else if(ch[i]=='#') {
              int r=1;
              
               int u = -1*(int)(ch[i+1]-'0');
            
            i++;
            if(i<ch.length-1){
                u *= -1;
                i++;
                while(i<ch.length-1 && !(preceed(ch[i])>0) && ch[i]!='#' && ch[i]!='&'){
                    
                    u*= Math.pow(10,1);
                   
                    
                    u = u + (int)(ch[i]-'0');
                    
                    i++;
                }
                u *=-1;
                i--;
            }
               a.push(u);
               
               
                    
        }else if(ch[i]=='&') {
           int r=1;
               
               int u = (int)(ch[i+1]-'0');
         
              
            i++;
            if(i<ch.length-1){
                i++;
                while(i<ch.length-1 && !(preceed(ch[i])>0) && ch[i]!='#' && ch[i]!='&'){
                    
                    u*= Math.pow(10,1);
                    
                    u+= (int)(ch[i]-'0');
                    i++;
                    
                }
                i--;
            }
          
          
               a.push(u);
            
          
      }else {
          
        y = (int)a.pop();
        x = (int)a.pop();
        switch(ch[i]) {
          case '+':
            a.push(x+y);
            break;
          case '-':
              
            a.push(x-y);
            break;
          case '*':
            a.push(x*y);
            break;
          case '/':{
            
                a.push(x/y);
            
          }; break;
          case '^': 
            if(y<0){
               a.push(0) ;
            }else{
            a.push((int)Math.pow(x,y));
                }
            break;    
                
        }
           }
      
    }
    return (int)a.pop();
  }
    
    
    
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        Evaluator obj = new Evaluator();
        String expression = scan.nextLine();
        
        
            expression = expression.replace("+--","+");
            if(expression.startsWith("--")){
               expression = expression.replaceFirst("--","");  
            }
             expression = expression.replace("*--","*"); 
            expression = expression.replace("/--","/"); 
            expression = expression.replace("^--","^");
            if(!(expression.startsWith("--"))&&expression.contains("--")){
                expression = expression.replaceAll("--","+");
            }
        
        
        try{
          char d[] = expression.toCharArray();  
        if(d[0]=='('){
            
        
        }else if( (d[0]!='-'&&(obj.preceed(d[0]) > 0)) || (obj.preceed(d[d.length - 1]) > 0) && d[d.length -1 ]!=')' ){
            throw new NullPointerException("Error");
        }    
           
             
        for(int i=0;i<d.length-1;i++){
            
            if((d[i]=='+' || d[i]=='*' || d[i]=='/' || d[i]=='^') && (d[i+1]=='-')){
             
                throw new NullPointerException("Error");
            }
            if(obj.preceed(d[i]) > 0 && d[i]!=')'){
               if( (d[i+1] =='*') ||(d[i+1]=='/') || (d[i+1]=='^') ||(d[i+1] == '+')){
                throw new NullPointerException("Error");
               }
            }
            
            if(((d[i]=='(' && i>0 ) && (d[i-1]=='a' || d[i-1]=='b' || d[i-1]=='c')) ||(( d[i]==')') && (d[i+1]=='a' || d[i+1]=='b' || d[i+1]=='c'))){
                throw new NullPointerException("Error");
            }
            if((d[i]=='a' || d[i]=='b' || d[i]=='c') && (d[i+1]=='a' || d[i+1]=='b' || d[i+1]=='c') ){
                 throw new NullPointerException("Error");
            }
           
        }
            

        System.out.println(obj.infixToPostfix(expression));
        
         obj.i1=scan.nextLine();
         obj.i2=scan.nextLine();    
         obj.i3=scan.nextLine();
         
        
         System.out.println(obj.evaluate(obj.infixToPostfix(expression)));
         
        }catch(Exception ex){
            System.out.println("Error");
        }   
        
        
    }
   
    
    

    

   
}
