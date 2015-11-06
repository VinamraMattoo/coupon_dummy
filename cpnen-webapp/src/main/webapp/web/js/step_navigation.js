	$('#second_back').click(function() {
		$('#first_step').slideDown();
		$('#second_step').slideUp();
		updateProgressBar(0, 0);
	});

	$('#third_back').click(function() {
		$('#second_step').slideDown();
		$('#third_step').slideUp();
		updateProgressBar(33, 113);
	});

	$('#fourth_back').click(function() {
		$('#third_step').slideDown();
		$('#fourth_step').slideUp();
		updateProgressBar(66, 226);
	});

	$('#submit_first').click(function(){
		$('#first_step').slideUp();
		$('#second_step').slideDown();
		updateProgressBar(33, 113);
	});

  	$('.directoryTree_print').click(
  			function(e) {
  				selectedProdData = ('"'
  						+ $('#directoryTree_container').data('directoryTree')
  								.selectedItems().join('", "') + '"');
  				processSecondStep();
  			});

function updateProgressBar(percent, pixel) {
		var compPercent = percent + '% Complete';
		var compPixel = pixel + 'px';
		$('#progress_text').html(compPercent);
		$('#progress').css('width', compPixel);
	}

     function processSecondStep() {
        		// update progress bar
        		updateProgressBar(66, 226);
        		// slide steps
        		$('#second_step').slideUp();
        		$('#third_step').slideDown();
        	}

        		// third step
            	$('#submit_third').click(function() {
            							var flatVal = 0;
                                        				var percentDiscount = 0;

                                        				var dis = $("#ruleType option:selected").text();
                                        				if (dis == "FLAT") {
                                        					flatVal = $('#flatAmount').val();
                                        				} else {
                                        					percentDiscount = $('#percent').val();
                                        				}

                                        				updateProgressBar(100, 339);
                                        				// prepare the fourth step
                                        				var fields = [ $('#name').val(), $('#app_from').val(),
                                        						$('#app_till').val(), $('#prodMaxCount').val(),
                                        						$('#prodMinCount').val(), $('#transValMax').val(),
                                        						$("#inc option:selected").text(),
                                        						$("#span option:selected").text(),
                                        						$('#transMinVal').val(), $('#desc').val(),
                                        						selectedProdData,
                                        						$("#ruleType option:selected").text(),
                                        						$('#ruleDesc').val(), percentDiscount, flatVal,
                                        						$('#priority').val()



                                        				];

                                        				var tr = $('#fourth_step tr');
                                        				tr.each(function() {
                                        					// alert( fields[$(this).index()] )
                                        					$(this).children('td:nth-child(2)').html(
                                        							fields[$(this).index()]);

                                        				});

                                        				// slide steps
                                        				$('#third_step').slideUp();
                                        				$('#fourth_step').slideDown();
            			});

            	// fourth step
            	$('#submit_fourth').click(function() {
            		// send information to server
            		alert('Creating Coupon..');
            	});