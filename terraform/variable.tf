variable "region" {
  default = "eu-west-1"
}

variable "lambda_payload_filename" {
  default = "../build/libs/sainsburys-all.jar"
}

variable "lambda_function_handler" {
  default = "com.github.davetrencher.GetProductsLambdaHandler::handleRequest"
}

variable "lambda_runtime" {
  default = "java11"
}

variable "api_path" {
  default = "getProducts"
}

variable "http_method" {
  default = "GET"
}

variable "account_id" {

}

variable "stage_name" {
  default = "dev"
}