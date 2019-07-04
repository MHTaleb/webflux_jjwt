import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JwtComponent } from './jwt/jwt.component';
import { JwtService } from './jwt.service';

@NgModule({
  declarations: [JwtComponent],
  imports: [
    CommonModule
  ],
  providers: [JwtService]
})
export class AuthModule { }
