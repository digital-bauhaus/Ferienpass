import Swal from 'sweetalert2';

import '@sweetalert2/theme-bootstrap-4/bootstrap-4.scss';

const TechnicalProblemsModal = Swal.mixin({
  titleText: 'Oh Nein!',
  html: 'Leider haben wir gerade technische Schwierigkeiten.<br>Bitte versuchen Sie es später nochmal.',
  icon: 'error',
  position: 'top',
  showConfirmButton: false,
  allowOutsideClick: false,
  allowEscapeKey: false,
});

const TechnicalProblemsDialog = Swal.mixin({
  titleText: 'Oh nein!',
  text: 'Da ist leider etwas unerwartetes schiefgegangen.<br>Bitte versuchen Sie es später nochmal.',
  icon: 'error',
  showCancelButton: false,
  confirmButtonText: 'Ok',
  focusConfirm: true,
  customClass: {
    confirmButton: 'btn btn-primary mx-2',
  },
  buttonsStyling: false,
});

const FailureDialog = Swal.mixin({
  titleText: 'Oh nein!',
  icon: 'error',
  showCancelButton: false,
  confirmButtonText: 'Ok',
  focusConfirm: true,
  customClass: {
    confirmButton: 'btn btn-primary mx-2',
  },
  buttonsStyling: false,
});

const SuccessDialog = Swal.mixin({
  titleText: 'Geschafft!',
  icon: 'success',
  showCancelButton: false,
  confirmButtonText: 'Ok',
  focusConfirm: true,
  customClass: {
    confirmButton: 'btn btn-primary mx-2',
  },
  buttonsStyling: false,
});

const FailureToast = Swal.mixin({
  toast: true,
  icon: 'error',
  position: 'top-end',
  showConfirmButton: false,
  timer: 5000,
});

const SuccessToast = Swal.mixin({
  toast: true,
  icon: 'success',
  position: 'bottom',
  showConfirmButton: false,
  timer: 3000,
  timerProgressBar: true,
  onOpen: (toast) => {
    toast.addEventListener('mouseenter', Swal.stopTimer);
    toast.addEventListener('mouseleave', Swal.resumeTimer);
  },
});

const DeleteDialog = Swal.mixin({
  titleText: 'Wirklich löschen?',
  icon: 'warning',
  showCancelButton: true,
  confirmButtonText: 'Ja, löschen!',
  cancelButtonText: 'Nein, nicht löschen.',
  focusConfirm: false,
  focusCancel: true,
  customClass: {
    confirmButton: 'btn btn-danger mx-2',
    cancelButton: 'btn btn-secondary mx-2',
  },
  buttonsStyling: false,
});

export {
  Swal, SuccessToast, FailureToast,
  DeleteDialog, SuccessDialog, FailureDialog, TechnicalProblemsModal, TechnicalProblemsDialog,
};
