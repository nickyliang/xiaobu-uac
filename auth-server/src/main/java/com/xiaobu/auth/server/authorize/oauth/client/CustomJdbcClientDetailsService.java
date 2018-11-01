package com.xiaobu.auth.server.authorize.oauth.client;

import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * @author qichao
 * @create 2018-10-31
 **/
public class CustomJdbcClientDetailsService extends JdbcClientDetailsService {

	private static final String SELECT_CLIENT_DETAILS_SQL = "select client_id, client_secret, resource_ids, scope, grant_types, " +
			"redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove " +
			"from fs_oauth_client where client_id = ? and archived = 0 ";

	public CustomJdbcClientDetailsService(DataSource dataSource) {
		super(dataSource);
		setSelectClientDetailsSql(SELECT_CLIENT_DETAILS_SQL);
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
		return super.loadClientByClientId(clientId);
	}


	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		super.updateClientDetails(clientDetails);
	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		super.updateClientSecret(clientId, secret);
	}

	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		super.removeClientDetails(clientId);
	}
}
