/**
 * Crown Copyright (C) 2008 - 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.nhs.cfh.dsp.srth.desktop.modules.scripting.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.*;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * A wrapper that is {@link org.springframework.context.ApplicationContextAware}, to inject
 * references to services.
 *
 * Based on an example by Bruce Fancher
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jan 6, 2011 at 6:29:18 PM
 */
public class ApplicationContextWrapper implements ApplicationContextAware {

    private ApplicationContext context;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public boolean containsBean(String arg0) {
        return context.containsBean(arg0);
    }

    public boolean containsBeanDefinition(String arg0) {
        return context.containsBeanDefinition(arg0);
    }

    public String[] getAliases(String arg0) throws NoSuchBeanDefinitionException {
        return context.getAliases(arg0);
    }

    public Object getBean(String arg0, Class arg1) throws BeansException {
        return context.getBean(arg0, arg1);
    }

    public Object getBean(String arg0) throws BeansException {
        return context.getBean(arg0);
    }

    public int getBeanDefinitionCount() {
        return context.getBeanDefinitionCount();
    }

    public String[] getBeanDefinitionNames() {
        return context.getBeanDefinitionNames();
    }

    public String[] getBeanNamesForType(Class arg0, boolean arg1, boolean arg2) {
        return context.getBeanNamesForType(arg0, arg1, arg2);
    }

    public String[] getBeanNamesForType(Class arg0) {
        return context.getBeanNamesForType(arg0);
    }

    public Map getBeansOfType(Class arg0, boolean arg1, boolean arg2) throws BeansException {
        return context.getBeansOfType(arg0, arg1, arg2);
    }

    public Map getBeansOfType(Class arg0) throws BeansException {
        return context.getBeansOfType(arg0);
    }

    public String getDisplayName() {
        return context.getDisplayName();
    }

    public String getMessage(MessageSourceResolvable arg0, Locale arg1) throws NoSuchMessageException {
        return context.getMessage(arg0, arg1);
    }

    public String getMessage(String arg0, Object[] arg1, Locale arg2) throws NoSuchMessageException {
        return context.getMessage(arg0, arg1, arg2);
    }

    public String getMessage(String arg0, Object[] arg1, String arg2, Locale arg3) {
        return context.getMessage(arg0, arg1, arg2, arg3);
    }

    public ApplicationContext getParent() {
        return context.getParent();
    }

    public BeanFactory getParentBeanFactory() {
        return context.getParentBeanFactory();
    }

    public Resource getResource(String arg0) {
        return context.getResource(arg0);
    }

    public Resource[] getResources(String arg0) throws IOException {
        return context.getResources(arg0);
    }

    public long getStartupDate() {
        return context.getStartupDate();
    }

    public Class getType(String arg0) throws NoSuchBeanDefinitionException {
        return context.getType(arg0);
    }

    public boolean isSingleton(String arg0) throws NoSuchBeanDefinitionException {
        return context.isSingleton(arg0);
    }

    public void publishEvent(ApplicationEvent arg0) {
        context.publishEvent(arg0);
    }
}