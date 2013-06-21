/*
 * Copyright 2008-2011 UnboundID Corp. All Rights Reserved.
 */
/*
 * Copyright (C) 2008-2011 UnboundID Corp. This program is free
 * software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License (GPLv2 only) or the terms of the GNU
 * Lesser General Public License (LGPLv2.1 only) as published by the
 * Free Software Foundation. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses>.
 */

package samplecode.add;

import com.unboundid.ldap.sdk.*;
import com.unboundid.util.LDAPTestUtils;


/**
 * Demonstrates the {@link AddRequest}; this example uses a hard coded
 * hostname of {@code ldap-server.ldap.com} and port {@code 389} and
 * attempts to add {@code uid=user,dc=example,dc=com}.
 */
public final class AddExample {

  /**
   * demonstrate the {@link AddRequest}
   */
  public static void main(final String... args) {

    // Use connection options to specify that the connection attempt
    // should be 1 second and if the ADD request times out, the request
    // should be abandoned.
    final LDAPConnectionOptions connectionOptions = new LDAPConnectionOptions();
    connectionOptions.setAbandonOnTimeout(true);
    connectionOptions.setConnectTimeoutMillis(AddExample.OP_TIMEOUT_MILLIS);

    final String host = AddExample.HOSTNAME;
    final int port = AddExample.PORT;
    int result;
    LDAPResult ldapResult = null;
    try {

      // Connect to the server.
      final LDAPConnection ldapConnection =
        new LDAPConnection(connectionOptions,host,port);
      try {

        final Entry entry = LDAPTestUtils.generateUserEntry("test-user-id",
          "ou=people,dc=example,dc=com","Barbara","Jensen","password");

        final AddRequest addRequest = new AddRequest(entry);

        // Transmit the AddRequest to the server.
        ldapResult = ldapConnection.add(addRequest);

        System.out.println(ldapResult);
      } finally {
        ldapConnection.close();

        // Convert the result code to an integer for use in the exit method.
        result = ldapResult == null ? 1 : ldapResult.getResultCode().intValue();
      }
    } catch(final LDAPException e) {
      System.err.println(e);
      result = e.getResultCode().intValue();
    }

    System.exit(result);
  }



  /**
   * The host where the LDAP Directory Server listens for client connections
   */
  private static final String HOSTNAME = "ldap-server.ldap.com";


  /**
   * The maximum number of milliseconds to wait before abandoning the
   * AddRequest
   */
  private static final int OP_TIMEOUT_MILLIS = 1000;


  /**
   * The port on which the server at {@code HOSTNAME} listens for client
   * connections
   */
  private static final int PORT = 389;


}