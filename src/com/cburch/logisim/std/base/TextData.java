package com.cburch.logisim.std.base;


import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstanceData;

public class TextData implements InstanceData
{
	String data;
	String old;

	public TextData() 
	{
		old = "";
	}
	
	public TextData(String data, String old)
	{
		this.data = data;
		this.old = old;
	}
	
	public void setValue(Value value) 
	{
	}
	
	public Value getValue() 
	{
		return null;
	}
	
	public String getData()
	{
		return data;
	}
	
	public void unescapeAndSet(String in)
	{
		if (in == old || in.equals(old)) return;
		data = doProcessString(in);
		old = in;
	}
	
	private String doProcessString(String text)
	{
		char[] str = text.toCharArray();
		
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < str.length; i++)
	    {
			if (str[i] == '\\')
		    {
				i++;
		        // hmm
				if (i >= str.length)
	            {
		                return "";
		        }
		        char num = 0;
		        char[] buf = new char[4];
		        switch(str[i])
		        {
		                case '\'': buffer.append('\''); break;
		                case '"': buffer.append('"'); break;
		                case '\\': buffer.append('\\'); break;
		                case 'b': buffer.append('\b'); break;
		                case 'f': buffer.append('\f'); break;
		                case 'n': buffer.append('\n'); break;
		                case 'r': buffer.append('\r'); break;
		                case 't': buffer.append('\t'); break;
		                case 'x':
		                    i++;
		                    if (i < str.length && str[i] >= '0' && str[i] <= '9' && ((str[i] >= 'A' && str[i] <= 'F') || (str[i] >= 'a' && str[i] <= 'z'))) 
		                    {
		                        buf[0] = str[i];

		                        if (i + 1 < str.length && str[i+1] >= '0' && str[i+1] <= '9' && ((str[i+1] >= 'A' && str[i+1] <= 'F') || (str[i+1] >= 'a' && str[i+1] <= 'z')))
		                        {
		                            i++;
		                            buf[1] = str[i];
		                        }
		                        num = (char)Integer.parseInt(String.valueOf(buf), 16);
		                    }
		                    else
		                    {
		                    	return "";
		                    }
		                    buffer.append(num);
		                    break;
		                case '0':
		                case '1':
		                case '2':
		                case '3':
		                case '4':
		                case '5':
		                case '6':
		                case '7':
		                    buf[0] = str[i];

		                    if (i + 1 < str.length && str[i + 1] >= '0' && str[i + 1] <= '7')
		                    {
		                        i++;
		                        buf[1] = str[i];
		                        if (i + 1 < str.length && str[i + 1] >= '0' && str[i + 1] <= '7')
		                        {
		                            i++;
		                            buf[2] = str[i];
		                        }
		                    }
		                    num = (char)Integer.parseInt(String.valueOf(buf), 8);
		                    buffer.append(num);
		                    break;
		                default:
		                	return "";
		            }
		        }
		        else
		        {
		        	buffer.append(str[i]);
		        }
		    }

			
		   return buffer.toString();
	}

	
	public TextData clone()
	{
		return new TextData(data, old);
	}
}