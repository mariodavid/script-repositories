package com.company.rnd.scriptrepo.repository.provider;

import com.company.rnd.scriptrepo.entity.PersistentScript;
import com.company.rnd.scriptrepo.repository.factory.ScriptProvider;
import com.haulmont.cuba.core.global.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class GroovyScriptDbProvider implements ScriptProvider {

    private static final Logger log = LoggerFactory.getLogger(GroovyScriptDbProvider.class);

    @Inject
    private DataManager dataManager;

    @Override
    public String getScript(Class<?> scriptRepositoryClass, String methodName) {
        String scriptName = scriptRepositoryClass.getSimpleName() + "." + methodName;
        PersistentScript script = dataManager.load(PersistentScript.class)
                .query("select s from scriptrepo$PersistentScript s where s.name = :name")
                .parameter("name", scriptName)
                .one();
        log.trace(script.getName()+" "+script.getSourceText());
        return script.getSourceText();
    }
}
