/*
Copyright 2009-2016 Igor Polevoy

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at 

http://www.apache.org/licenses/LICENSE-2.0 

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License. 
*/
package org.javalite.activeweb;


import org.javalite.activeweb.controller_filters.HttpSupportFilter;

/**
 * Use this as a super class for integration tests that do not require a DB connection.
 * An integration test allows to describe a scenario of actions that span multiple controllers.
 *
 *
 * <p>
 *     <strong>Deprecated:</strong> use {@link AppSpec}.
 * </p>
 *
 * @author Igor Polevoy
 */
@Deprecated
public class IntegrationSpec extends RequestSpecHelper {

    public IntegrationSpec() {
        Configuration.resetFilters();
        Configuration.setInjector(null);
    }

    /**
     * Clears all filters from context even if they are defined in the <code>AppControllerConfig</code> class.
     * This method allows to run  integration specs cleanly, with filters specifically set by {@link #addFilter(Class, HttpSupportFilter)}
     * method.
     */
    protected void resetFilters(){
        Configuration.resetFilters();
    }

    protected RequestBuilder controller(String controllerName){
        return new RequestBuilder(controllerName, session());
    }

    @Override
    protected void setTemplateLocation(String templateLocation){
        Configuration.getTemplateManager().setTemplateLocation(templateLocation);
    }

    /**
     * Adds a filter to a specific controller for the duration of the current spec.
     * If you want a clean  execution (just the filters you added), do not forget to run {@link #resetFilters()} method
     * before this one.
     *
     * @param controllerClass class of controller
     * @param filter instance of a filter to add
     */
    protected void addFilter(Class<? extends AppController> controllerClass, HttpSupportFilter filter){
        Configuration.getFilterMetadata(filter).addController(controllerClass);
        Configuration.addFilter(filter);
    }

    /**
     * Adds a global (for any controller) filter for the duration of the current spec.
     * If you want a clean  execution (just the filters you added), do not forget to run {@link #resetFilters()} method
     * before this one.
     *
     * @param filter instance of a filter to add
     */
    protected void addFilter(HttpSupportFilter filter){
        Configuration.getFilterMetadata(filter);
        Configuration.addFilter(filter);
    }

    /**
     * Convenience method. Calls {@link #addFilter(HttpSupportFilter)} for each argument.
     *
     * @param filters a number of filters to add to this test.
     */
    protected void addFilters(HttpSupportFilter ... filters){
        for (HttpSupportFilter httpSupportFilter : filters) {
            addFilter(httpSupportFilter);
        }
    }
}
