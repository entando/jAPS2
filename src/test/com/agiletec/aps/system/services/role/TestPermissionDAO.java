/*
*
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
* This file is part of jAPS software.
* jAPS is a free software; 
* you can redistribute it and/or modify it
* under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
* 
* See the file License for the specific language governing permissions   
* and limitations under the License
* 
* 
* 
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
*/
package test.com.agiletec.aps.system.services.role;

import java.util.Iterator;
import java.util.Map;

import javax.sql.DataSource;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.system.services.role.Permission;
import com.agiletec.aps.system.services.role.PermissionDAO;

/**
 * @version 1.0
 * @author M.Diana
 */
public class TestPermissionDAO extends BaseTestCase {
	
    public void testAddUpdateDeletePermission() throws Throwable {
    	DataSource dataSource = (DataSource) this.getApplicationContext().getBean("servDataSource");
		PermissionDAO permissionDao = new PermissionDAO();
		permissionDao.setDataSource(dataSource);
		try {
            permissionDao.deletePermission("temp");
        } catch (Throwable t) {
        	throw t;
        }
		Permission permission = new Permission();
		permission.setName("temp");
		permission.setDescription("temp");
        try {
        	permissionDao.addPermission(permission);
        } catch (Throwable t) {
        	throw t;
        }
		Map<String, Permission> permissions = null;
        try {
        	permissions = permissionDao.loadPermissions();
        } catch (Throwable t) {
        	throw t;
        }
        Iterator<Permission> iter = permissions.values().iterator();
        boolean contains = false;
        while (iter.hasNext()) {
			permission = iter.next();
			if (permission.getName().equals("temp")) {
				contains = true;
			}
		}
        assertTrue(contains);
        this.updatePermission(permissionDao);
        this.deletePermission(permissionDao);
	}
	
	private void updatePermission(PermissionDAO permissionDao) throws Throwable {
		Permission permission = new Permission();
		permission.setName("temp");
		permission.setDescription("temp1");
        try {
        	permissionDao.updatePermission(permission);
        } catch (Throwable t) {
        	throw t;
        }
        Map<String, Permission> permissions = null;
        try {
        	permissions = permissionDao.loadPermissions();
        } catch (Throwable t) {
        	throw t;
        }
        Iterator<Permission> iter = permissions.values().iterator();
        boolean contains = false;
        while (iter.hasNext()) {
			permission = iter.next();
			if (permission.getDescription().equals("temp1")) {
				contains = true;
			}
		}
        assertTrue(contains);
	}
		
	private void deletePermission(PermissionDAO permissionDao) throws Throwable {
		Permission permission = new Permission();
		permission.setName("temp");
        try {
        	permissionDao.deletePermission(permission);
        } catch (Throwable t) {
        	throw t;
        }
        Map<String, Permission> permissions = null;
        try {
        	permissions = permissionDao.loadPermissions();
        } catch (Throwable t) {
        	throw t;
        }
        Iterator<Permission> iter = permissions.values().iterator();
        boolean contains = false;
        while (iter.hasNext()) {
			permission = iter.next();
			if (permission.getName().equals("temp")) {
				contains = true;
			}
		}
        assertFalse(contains);    
	}		
    	
}
