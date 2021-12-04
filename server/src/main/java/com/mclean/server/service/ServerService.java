package com.mclean.server.service;

import com.mclean.server.model.Server;

import java.io.IOException;
import java.util.Collection;

public interface ServerService {
    Server create(Server sever);
    Collection<Server> list(int limit);
    Server get(Long id);
    Server update (Server server);
    Boolean delete(Long id);
    Server ping (String ipAddress) throws IOException;

}
