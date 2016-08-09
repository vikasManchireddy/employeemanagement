package com.cg.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ServiceLocator {

   
	private ServiceLocator()
	{
		
	}
        
    
    
    public static DataSource getDataSource(String dataSourceJndiName) throws ServiceLocatorException {
       
		DataSource dataSource = null;

        try {
            Context ctx = new InitialContext();
            Context envContext  = (Context)ctx.lookup("java:/comp/env");
			
         
            dataSource=(DataSource) envContext.lookup(dataSourceJndiName);
            
            
        } catch (ClassCastException cce) {
            
        	System.out.println("classCast");
        	
        } catch (NamingException ne) {
            
        	System.out.println("namingexception");
        }
		
        return dataSource;
        
    }


   



}
