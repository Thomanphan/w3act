package controllers;

import play.Logger;
import play.Play;
import play.libs.ws.WS;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.ws.WSResponse;
import play.libs.ws.WSRequestHolder;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import uk.bl.exception.ActException;

public class WaybackController extends Controller {

	@Security.Authenticated(SecuredController.class)
	public static Promise<Result> wayback(String url) throws ActException {
		String wayBackUrl = Play.application().configuration().getString("application.wayback.url");
		
		// Build up the wayback query:
		final String wayback = wayBackUrl + "/" + url;

		// Build up URL and copy over query parameters:
		WSRequestHolder holder = WS.url(wayback).setFollowRedirects(false);
		for( String key : request().queryString().keySet() ) {
			holder.setQueryParameter(key, request().getQueryString(key));
		}

		// GET
		Promise<WSResponse> responsePromise = holder.get();

		final Promise<Result> resultPromise = responsePromise.map(

				new Function<WSResponse, Result>() {

					public Result apply(WSResponse response) {

						Logger.debug(wayback + " (" + response.getStatusText() + " " + response.getStatus() + ") " + response.getUri());

						Logger.debug("WS.Response: "+response);
						// TODO Copy all headers over?
						if ( response.getHeader(LOCATION) != null ) {
							Logger.debug("Copying over Location header: "+response.getHeader(LOCATION));
							response().setHeader(LOCATION, response.getHeader(LOCATION));
							return status(response.getStatus());
						}
						String contentType = response.getHeader(CONTENT_TYPE);
						Logger.debug("content type: " + contentType);
						return status(response.getStatus(), response.getBodyAsStream()).as(contentType);
					}

				}
				);
		return resultPromise;
	}

	public static Promise<Result> waybackRoot() throws ActException {
		return wayback("");
	}

}
