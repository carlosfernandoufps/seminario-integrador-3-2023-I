import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styles: [`a { color: #aa1916; }`]
})
export class FooterComponent {

  anio: number = new Date().getFullYear();

}
