package modules;

import com.google.inject.AbstractModule;

import services.UserService;
import services.impl.UserServiceImpl;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class).asEagerSingleton();
    }
}
