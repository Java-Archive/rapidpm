package org.rapidpm.tools;

/**
 * RapidPM - www.rapidpm.org
 * User: svenruppert
 * Date: 12/18/10
 * Time: 8:58 AM
 * This is part of the RapidPM - www.rapidpm.org project. please contact sven.ruppert@neoscio.de
 */

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class XmlRpcExecuter<T> {
    private static final Logger logger = Logger.getLogger(XmlRpcExecuter.class);

    private final String url;

    protected XmlRpcExecuter(String url) {
        this.url = url;
    }

    public abstract String getCalledServiceName();

    public abstract Object[] createParams();

    public T execute() {
        final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        T result = null;
        try {

            config.setServerURL(new URL(url));
            config.setEnabledForExtensions(true);
            config.setConnectionTimeout(60 * 1000);
            config.setReplyTimeout(60 * 1000);

            final XmlRpcClient client = new XmlRpcClient();

            client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
            client.setConfig(config);

            final Object[] params = createParams();

            result = (T) client.execute(getCalledServiceName(), params);

        } catch (XmlRpcException | MalformedURLException e) {
            logger.error(e);
        }
        return result;
    }


}