# Lucent Client

[![MIT License](https://badgen.net/npm/license/lodash?color=yellow&label=License)](https://opensource.org/licenses/)
![](https://img.shields.io/badge/Frontend-Vue-informational?style=flat&logo=vuejs&logoColor=42b883&color=42b883)

Lucent Client Live URL : http://lucent-frontend.s3-website.us-east-2.amazonaws.com




## Libraries Used
-    TailwindCSS - Styling
-    Pinia - State managemnt
-    Axios - Request handling
-    VueRouter - Routing in the app
-    Headless UI - For modals

---


<a href="https://ibb.co/QH7zbq8"><img src="https://i.ibb.co/dps1W3b/Lucent.png" alt="Lucent" border="0"></a>



## Brief

 We frequently find ourselves donating to charity , with the expectation that the organization would spend money on our behalf for social welfare. However, ***most of the time, we have no idea where the money is going***. They are obligated to use the funds for our intended purpose. Our approach addresses this issue by increasing the system's transparency.

 People can use the website to contribute money to their preferred charity or to pay their taxes. The money can be collected by the organization. When the organization collects the money, they will be required to provide a reason for doing so. ***The system will then inform contributors about the organization's collection*** by text message. As a result, individuals will know exactly where their money is being spent.

 This brings ***transparency*** to the whole operation. People get to know where their money is going and the charities can give feedback to the people on where they’re spending people’s money. Furthermore, by visiting an organization's page, individuals may check their spending records and reasons. They will be able to view the top contributors, the most recent donations, and other relevant information.

---

## Workflow

- **Organization**
  - To create an organiztion, manager first have to go to [the registrion page](https://lucent-frontend.s3-website.us-east-2.amazonaws.com/orgreg).
  - The registration will be created in a "Pending" State.
  - After that, Admin will have the authority to publish the orgnization.
  - Once published, the organization will be available to to the public.
  - If membership auto approval is off, Organization manager will have to check and accept each of the membership request so that the members can donate.
  - Organization manager can spend an amount of money which is lower or equal to the balance of the organization only by specifying a reason to spend.

- **Donations**
  - To donate to a charity, users first have to log in to the site.
  - Select a charity and request for membership.
  - If that organization's auto approval is on, the request will be accepted automaticaaly otherwise he/she will have to wait to be accepted as a member before making donations.
  - Once a member, users can donate. And when their money is being spent by the organization they will receive a text message in their phone. 
