import 'package:flutter/material.dart';
import 'dart:async';
import './login/login.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();
    _startSplashScreen();
  }

  void _startSplashScreen() async {
    var duration = const Duration(seconds: 6);
    Timer(duration, () {
      Navigator.of(context).pushReplacement(
        MaterialPageRoute(builder: (_) => const Login()),
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF9bafae), // Color de fondo #9bafae
      body: Center(
        child: Image.asset(
          'assets/logo.png',
          width: 1024.0, // Ancho del logo según sus dimensiones originales
          height: 1024.0, // Alto del logo según sus dimensiones originales
        ),
      ),
    );
  }
}
