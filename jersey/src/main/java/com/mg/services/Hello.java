package com.mg.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.mg.dao.MgNodesDao;
import com.mg.entities.JsonTestObject;
import com.mg.entities.MgNode;
import com.mg.entities.RequestHello;
import com.mg.utils.Utils;

/**
 * Service for attaching nodes
 * 
 * Body example:
 * {"sernum":"00001", "voltageUsing":5,"currentUsing":1}
 * 
 * @author Saulius Alisauskas 2012-10-05
 *
 */
@Path("/hello")
public class Hello {
	
	@Context
	HttpServletRequest request;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {		
		return "Hello Jersey, request received from: " + request.getRemoteAddr();
	}
	
	@GET	
	@Path("/jsontest")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonTestObject getJsontest() {		
		return new JsonTestObject();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String sayPlainTextHelloPost(RequestHello r) {		
		logger.debug(r);
		MgNode node = new MgNode(Utils.getOriginalAddress(request), r.getSernum());
		node.setCurrentOut(r.getCurrentOut());
		node.setVoltageOut(r.getVoltageOut());
		node.setVoltageIn(r.getVoltageIn());
		node.setCurrentIn(r.getCurrentIn());
		MgNodesDao.addNode(node);		
		return "Hello from Jersey";
	}	
}
