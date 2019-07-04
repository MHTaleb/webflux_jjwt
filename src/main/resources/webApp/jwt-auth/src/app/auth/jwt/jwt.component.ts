import { Component, OnInit } from '@angular/core';
import { JwtService } from '../jwt.service';

@Component({
  selector: 'app-jwt',
  templateUrl: './jwt.component.html',
  styleUrls: ['./jwt.component.sass']
})
export class JwtComponent implements OnInit {

  constructor(private authService: JwtService) { }

  ngOnInit() {
  }

}
