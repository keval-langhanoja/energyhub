$('.categCheckbox').change(function(e) {

    var checked = $(this).prop("checked"),
        container = $(this).parent(),
        siblings = container.siblings();
  
    container.find('.categCheckbox').prop({
      indeterminate: false,
      checked: checked
    });
  
    function checkSiblings(el) {
  
      var parent = el.parent().parent(),
          all = true;
  
      el.siblings().each(function() {
        let returnValue = all = ($(this).children('.categCheckbox').prop("checked") === checked);
        return returnValue;
      });
      
      if (all && checked) {
  
        parent.children('.categCheckbox').prop({
          indeterminate: false,
          checked: checked
        });
  
        checkSiblings(parent);
  
      } else if (all && !checked) {
  
        parent.children('.categCheckbox').prop("checked", checked);
        parent.children('.categCheckbox').prop("indeterminate", (parent.find('.categCheckbox:checked').length > 0));
        checkSiblings(parent);
  
      } else {
  
        el.parents("li").children('.categCheckbox').prop({
          indeterminate: true,
          checked: false
        });
  
      }
  
    }
  
    checkSiblings(container);
  });