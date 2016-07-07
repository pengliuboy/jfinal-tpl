(function($){
	$.dialog = function(settings){
		var options = $.extend({
			selector: '#modalDiv',
			//header: 'please confirm',
			headerSelector: '.modal-header h3',
			body: '',
			bodySelector: '.modal-body p',
			callback : false
		}, settings);
		
		var modal = $(options.selector);
		if(options.header){
			$(options.headerSelector).html(options.header);
		}
		if(options.body){
			$(options.bodySelector).html(options.body);
		}
		
		var buttons = options.buttons;
		if (!buttons){
			buttons = [ {
				selector : '#confirmYesBtn',
				click : function() {
					if (options.callback) {
						options.callback();
					}
					modal.modal('hide');
				}
			} ];
		}
		
		$.each(buttons, function(i, b) {
			$(b.selector, modal).off('click').click(b.click);
		});
		
		modal.modal('show');
		return modal;
	}
})(jQuery)