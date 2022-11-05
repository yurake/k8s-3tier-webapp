#!/usr/bin/env bats

# @test "When no argument provided, it should end normally with exit code 0" {
#   run kubernetes/bin/apply-default-quarkus.sh
#   [ "$status" -eq 0 ]
# }

# @test "When arguments 'crc' provided, it should end normally with exit code 0" {
#   run kubernetes/bin/apply-default-quarkus.sh crc
#   [ "$status" -eq 0 ]
# }

@test "When incorrect argument provided, it should fail with exit code 1" {
  run kubernetes/bin/apply-default-quarkus.sh error
  [ "$status" -eq 1 ]
}

@test "When more than 2 arguments provided, it should fail with exit code 1" {
  run kubernetes/bin/apply-default-quarkus.sh error error
  [ "$status" -eq 1 ]
}
