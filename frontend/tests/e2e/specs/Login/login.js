import { Then, When } from 'cypress-cucumber-preprocessor/steps';

When('I open the login page', () => {
  cy.visit('/#/login');
});

Then('I am prompted for my username and password', () => {
  cy.contains('label', 'Name');
  cy.contains('label', 'Passwort');
});
