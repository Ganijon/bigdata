package parts;

/**
 *
 * @author ganijon
 */

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Writable;

public class Stripe extends MapWritable {

	@Override 
	public String toString() {
		
		if(isEmpty()) return "{ }";
		
		StringBuilder sb = new StringBuilder("{ ");
		
		for(Writable key: keySet()) {
			sb.append(key.toString() + ": " + this.get(key));
			sb.append(", ");
		}
		sb.replace(sb.length() - 2, sb.length(), " }");
		
		return sb.toString();
	}
}
