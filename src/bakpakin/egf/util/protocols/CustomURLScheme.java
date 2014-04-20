package bakpakin.egf.util.protocols;

/*
 * The CustomURLScheme class has a static method for adding cutom protocol
 * handlers without getting bogged down with other class loaders and having to
 * call setURLStreamHandlerFactory before the next guy...
 */

import java.net.URLStreamHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Allows you to add your own URL handler without running into problems
 * of race conditions with setURLStream handler.
 * 
 * To add your custom protocol eg myprot://blahblah:
 * 
 * 1) Create a new protocol package which ends in myprot eg com.myfirm.protocols.myprot
 * 2) Create a subclass of URLStreamHandler called Handler in this package
 * 3) Before you use the protocol, call CustomURLScheme.add(com.myfirm.protocols.myprot.Handler.class);
 * @author jasonw
 */
public class CustomURLScheme
{

    // this is the package name required to implelent a Handler class
    private static Pattern packagePattern = Pattern.compile( "(.+\\.protocols)\\.[^\\.]+" );

    /**
     * Call this method with your handlerclass
     * @param handlerClass
     * @throws Exception 
     */
    public static void add( Class<? extends URLStreamHandler> handlerClass ) throws Exception
    {
        if ( handlerClass.getSimpleName().equals( "Handler" ) )
        {
            String pkgName = handlerClass.getPackage().getName();
            Matcher m = packagePattern.matcher( pkgName );

            if ( m.matches() )
            {
                String protocolPackage = m.group( 1 );
                add( protocolPackage );
            }
            else
            {
                throw new CustomURLHandlerException( "Your Handler class package must end in 'protocols.yourprotocolname' eg com.somefirm.blah.protocols.yourprotocol" );
            }

        }
        else
        {
            throw new CustomURLHandlerException( "Your handler class must be called 'Handler'" );
        }
    }

    private static void add( String handlerPackage )
    {
        // this property controls where java looks for
        // stream handlers - always uses current value.
        final String key = "java.protocol.handler.pkgs";

        String newValue = handlerPackage;
        if ( System.getProperty( key ) != null )
        {
            final String previousValue = System.getProperty( key );
            newValue += "|" + previousValue;
        }
        System.setProperty( key, newValue );
    }
}
