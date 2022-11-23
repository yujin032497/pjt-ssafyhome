//package com.ssafy.pjt.controller;
//
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Text;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ssafy.pjt.model.dto.Apt;
//import com.ssafy.pjt.model.dto.User;
//import com.ssafy.pjt.model.service.AptService;
//
//@RestController
//@RequestMapping("/api/apt")
//@CrossOrigin("*")
//public class AptRestController{
//
//	@Autowired
//	AptService service;
//
//	// 객체를 JSON 문자열로 변경하거나 반대로 JSON 문자열을 객체로 변경해주는 객체
//	private ObjectMapper mapper = new ObjectMapper();
//
//	/*
//	 * @Override protected void doGet(HttpServletRequest req, HttpServletResponse
//	 * resp) throws ServletException, IOException {
//	 *
//	 * String action = req.getParameter("action"); Object obj = null; // REST 방식 응답을
//	 * 위한 객체
//	 *
//	 * switch(action) { case "sido": obj = getSidoNames(req, resp); break;
//	 *
//	 * case "gugun": obj = getGuGunNames(req, resp); break;
//	 *
//	 * case "dong": obj = getDongNames(req, resp); break;
//	 *
//	 * case "getFavorites": obj = getFavorites(req, resp); break; case "getApt": obj
//	 * = getApt(req, resp); break; case "getActualPriceApt": getActualPriceApt(req,
//	 * resp); break; }
//	 *
//	 * if (obj != null) { // JSON 문자열로 응답 보내기 // 응답 헤더 작성 (Header)
//	 * resp.addHeader("Content-Type", "application/json; charset=UTF-8");
//	 *
//	 * // 응답 Payload 작성 // 1. 객체를 JSON 문자열로 직렬화 String json =
//	 * mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
//	 *
//	 * // 2. 직렬화 한 문자열을 응답 PrintWriter writer = resp.getWriter();
//	 * writer.write(json); }
//	 *
//	 * }
//	 * @Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		doGet(req, resp);
//	}
//	 */
//
////	private void getActualPriceApt(HttpServletRequest req, HttpServletResponse resp) {
////		String yyyymm = req.getParameter("DEAL_YMD");
////		String dongCode = req.getParameter("LAWD_CD");
////		String sorting = req.getParameter("SORT") == null ? "" : req.getParameter("SORT");
////		System.out.println("sort: "+sorting);
////		try {
////			List<Map<String, String>> items = service.getActualPriceApt(yyyymm, dongCode, sorting);
////			String xml = map2XML(items);
////			// 2. 직렬화 한 문자열을 응답
////			resp.addHeader("Content-Type", "application/xml; charset=UTF-8");
////			PrintWriter writer = resp.getWriter();
////			writer.write(xml);
////		} catch(Exception e) {
////			e.printStackTrace();
////		}
////	}
//	@GetMapping("/getActualPriceApt")
//	private ResponseEntity<?> getActualPriceApt(@RequestParam("LAWD_CD") String dongCode, @RequestParam("DEAL_YMD") String yyyymm, @RequestParam("SORT") String sorting) {
//		sorting = sorting == null ? "" : sorting;
//		System.out.println("sort: "+sorting);
//		try {
//			List<Map<String, Object>> items = service.getActualPriceApt(yyyymm, dongCode, sorting);
//			System.out.println(items.toString());
//			String xml = map2XML(items);
//			// 2. 직렬화 한 문자열을 응답
////			resp.addHeader("Content-Type", "application/xml; charset=UTF-8");
////			PrintWriter writer = resp.getWriter();
////			writer.write(xml);
//			return new ResponseEntity<>(xml, HttpStatus.OK);
//		} catch(Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	private String map2XML(List<Map<String, Object>> itemList) {
//		String XMLString = null;
//		try {
//			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
//			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
//			Document doc = docBuilder.newDocument();
//
//			Element items = doc.createElement("items");
//			Element item;
//			for(Map<String, Object> map : itemList) {
//				item = doc.createElement("item");
//				for(String key: map.keySet()) {
//					Element keyNode = doc.createElement(key);
//					String val = "";
//					if(map.get(key) instanceof Integer){
//						val = String.valueOf(map.get(key));
//					}
//					else{
//						val = map.get(key).toString();
//					}
//					Text textNode = doc.createTextNode(val);
//					keyNode.appendChild(textNode);
//					item.appendChild(keyNode);
//				}
//				items.appendChild(item);
//			}
//			doc.appendChild(items);
//
//			XMLString = getDOMToString(doc);
//
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return XMLString;
//	}
//
//	private static String getDOMToString(Document doc) throws Exception {
//		TransformerFactory transfac = TransformerFactory.newInstance();
//
//		Transformer trans = transfac.newTransformer();
//
//		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//
//		trans.setOutputProperty(OutputKeys.INDENT, "yes");
//
//		StringWriter sw = new StringWriter();
//
//		StreamResult result = new StreamResult(sw);
//
//		DOMSource source = new DOMSource(doc);
//		trans.transform(source, result);
//		return sw.toString();
//	}
//
//
//
//	public Object getSidoNames(HttpServletRequest req, HttpServletResponse resp) {
//		try {
//			Map<String, String> map = service.selectSidoNames();
//			return map;
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	public Object getGuGunNames(HttpServletRequest req, HttpServletResponse resp) {
//		String sidoCode = req.getParameter("sidoCode").substring(0, 2);
//
//		try {
//			Map<String, String> map = service.getGuGunNames(sidoCode);
//			return map;
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	public Object getDongNames(HttpServletRequest req, HttpServletResponse resp) {
//		String gugunCode = req.getParameter("gugunCode").substring(0, 5);
//		//System.out.println(gugunCode);
//
//		try {
//
//			Map<String, String> map = service.getDongNames(gugunCode);
//			return map;
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	private Object getFavorites(HttpServletRequest req, HttpServletResponse resp) {
//		User user = (User) req.getSession().getAttribute("user");
//
//
//		try {
//			Map<String, String> map = service.getFavorites(user.getId());
//			return map;
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private Object getApt(HttpServletRequest req, HttpServletResponse resp) {
//		String key = req.getParameter("dongCode");
//		String ym = req.getParameter("ym");
//
//		Map<String, List<Apt>> res = null;
//		try {
//			res = service.getApt(key, ym);
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return res;
//	}
//
//}
