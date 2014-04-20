package bakpakin.egf.framework;

import java.io.Serializable;

/**
 * The <code>Component</code> class represents data that is to be held by {@link Entity}s in the 
 * framework.  Ideally, all logic code that modifies a <code>Component</code> should be in an
 * {@link EntitySystem}; Components should be pure data.  Also note that subclasses of a
 * <code>Component</code> will not match the same {@link Matcher} as the superclass.
 * Within the framework, all subclasses of Component are handled without inheritance.
 * @author Calvin
 *
 */
public interface Component extends Serializable {
	
}
