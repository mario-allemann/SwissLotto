package lotto.changeLotteryTicket;

import javafx.scene.control.TextField;

/**A TextField with a simple additional String attribute **/

public class TextFieldWProperty extends TextField{
	public String property;
	
	public TextFieldWProperty(String defaultValue, String property) {
		super(defaultValue);
		this.property = property;
		
	}
	

}
