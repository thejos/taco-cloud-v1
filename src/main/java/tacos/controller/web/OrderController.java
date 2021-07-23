package tacos.controller.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.data.OrderRepository;
import tacos.model.bean.TacoOrder;

/**
 * @author Dejan Smiljić - dej4n.s@gmail.com <br>
 *         <br>
 *         The class-level <tt>@RequestMapping</tt> specifies that any
 *         request-handling methods in this controller will handle requests
 *         whose path begins with <tt>/orders</tt>. When combined with the
 *         method-level <tt>@GetMapping</tt>, it specifies that the
 *         <code>orderForm</code> method will handle HTTP GET requests for
 *         <tt>/orders/current</tt>.
 * 
 * @see #orderForm(Model)
 */
@Controller
@RequestMapping(path = "/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	/**
	 * 
	 * @param
	 * @return <b>String</b> - the value returned indicates a view that will be
	 *         shown to the user. <br>
	 *         <br>
	 *         The <code>orderForm</code> view is provided by a Thymeleaf template
	 *         named <tt>orderForm.html</tt><br>
	 *         <br>
	 *         <strong>Most request-handling methods conclude by returning the
	 *         logical name of a view, to which the request (along with any model
	 *         data) is forwarded.</strong>
	 */
	@GetMapping(path = "/current")
	public String orderForm() {
		return "orderForm";
	}

	/**
	 * 
	 * @param tacoOrder
	 * @param errors
	 * @param sessionStatus
	 * @return <b>String</b> - the value returned indicates a view that will be
	 *         shown to the user. <br>
	 *         <br>
	 *         This method saves the <code>TacoOrder</code> object via the
	 *         <code>save</code> method on the injected
	 *         <code>OrderRepository</code>. The <code>TacoOrder</code> object is
	 *         submitted in the form (<tt>orderForm</tt>). It is the same
	 *         <code>TacoOrder</code> object maintained in <tt>session</tt>.<br>
	 *         Once the <code>TacoOrder</code> is saved (persisted), it is not
	 *         needed in session anymore. The <code>processOrder</code> method asks
	 *         for a <code>SessionStatus</code> parameter and calls its
	 *         <code>setComplete</code> method to reset the session.<br>
	 *         If it's not cleaned out, the <code>TacoOrder</code> object remains in
	 *         session, including its associated <tt>Taco</tt> objects, so the next
	 *         order will start with whatever tacos the old order contained.
	 */
	@PostMapping
	public String processOrder(@Valid TacoOrder tacoOrder, Errors errors, SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "orderForm";
		}

		TacoOrder persistedOrder = orderRepository.save(tacoOrder);
		logger.info("\nOrder submitted: " + persistedOrder);
		sessionStatus.setComplete();
		return "redirect:/";
	}

}
