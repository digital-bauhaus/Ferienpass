import { FailureDialog, TechnicalProblemsDialog } from '@/modules/sweet-alert';

const buildListHtmlFromErrors = function buildListHtmlFromErrors(errors) {
  let errorHtml = '<ul class="text-left">';
  errors.forEach((error) => {
    errorHtml += `<li>${error.defaultMessage}</li>`;
  });
  errorHtml += '</ul>';
  return errorHtml;
};

const handleCommonServerError = function handleCommonServerError(error) {
  // see: https://github.com/axios/axios#handling-errors
  if (error.response) {
    console.log(error.response);
    switch (error.response.status) {
      case 400:
        if (error.response.data?.errors) {
          // validation error
          FailureDialog.fire({
            icon: 'warning',
            titleText: 'Bitte korrigieren Sie folgende Fehler: ',
            html: buildListHtmlFromErrors(error.response.data.errors),
          });
        } else {
          // other spring errorMessages
          TechnicalProblemsDialog.fire();
        }
        break;
      case 422:
        // custom validation error
        FailureDialog.fire({
          icon: 'warning',
          text: error.response.data.message,
        });
        break;
      default:
        TechnicalProblemsDialog.fire();
        break;
    }
  } else {
    // Problem is with Axios or we got no response at all
    TechnicalProblemsDialog.fire();
  }
};

export default handleCommonServerError;
